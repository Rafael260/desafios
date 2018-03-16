/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.notificador;

import com.mycompany.crawlers.consumidor.ConsumidorRedditSelenium;
import com.mycompany.crawlers.consumidor.IConsumidorReddit;
import com.mycompany.crawlers.exceptions.NotificacaoException;
import com.mycompany.crawlers.util.Util;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rafao
 */
public class NotificadorTelegramAPI implements INotificador {

    public static final String TOKEN = "576007541:AAGp2H8ohSmCQ3B4g6psRzBVMJcpUaps0QM";
    public static final String COMANDO = "/nadaprafazer";
    public static final int NUMERO_TENTATIVAS_RESPOSTA = 8;
    private TelegramBot bot;

    public NotificadorTelegramAPI() {
        bot = new TelegramBot(TOKEN);
    }

    @Override
    public void esperarComandos(){
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                System.out.println("Vai processar as notificações");
                String mensagemRecebida;
                for (Update update : updates) {
                    mensagemRecebida = update.message().text();
                    if (mensagemRecebida == null) {
                        //nao responder...
                        continue;
                    }
                    System.out.println("Mensagem recebida: " + mensagemRecebida);
                    //Se for um pedido para enviar as threads do Reddit
                    if (mensagemRecebida.toLowerCase().contains(COMANDO)) {
                        System.out.println("Texto recebido contém o comando para busca");
                        String[] comandosMensagem = mensagemRecebida.split(" ");
                        //Se passar os parametros corretamente, podemos ver
                        if (comandosMensagem.length == 2) {
                            responder(update, Arrays.asList(new String[]{"Ok, vou checar esses fóruns pra você"}));
                            List<String> listaDeTheadsEmAlta = coletarListaDeThreads(comandosMensagem[1]);
                            if (!listaDeTheadsEmAlta.isEmpty()) {
                                responder(update, listaDeTheadsEmAlta);
                            } else {
                                responder(update, Arrays.asList(
                                        new String[]{"Não encontrei nenhuma thread relevante"}));
                            }
                        } else {
                            responder(update, Arrays.asList(new String[]{"Quais subreddits você quer que eu procure? "
                                    + "O padrão do comando é " + COMANDO + " lista de subs separados por ponto e virgula"}));
                        }
                    } else {
                        responder(update, Arrays.asList(new String[]{update.message().text()}));
                    }
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
        
        while (true) {
            Util.esperar(10000);
        }
    }

    private List<String> coletarListaDeThreads(String subReddits) {
        IConsumidorReddit consumidor = new ConsumidorRedditSelenium();
        return consumidor.coletarThreadsEmAlta(subReddits);
    }

    private void responder(Update update, List<String> mensagens) {
        Long chatId;
        SendMessage request;
        for (String mensagem : mensagens) {
            chatId = update.message().chat().id();
            request = new SendMessage(chatId, mensagem)
                    .parseMode(ParseMode.HTML)
                    .disableWebPagePreview(true)
                    .disableNotification(true);
            enviarResposta(request);
        }
    }

    private void enviarResposta(SendMessage request) {
        int tentativas = NUMERO_TENTATIVAS_RESPOSTA;
        while (tentativas-- > 0) {
            System.out.println("Tentando responder...");
            SendResponse response = bot.execute(request);
            if (response.isOk()) {
                System.out.println("Resposta OK");
                return;
            } else {
                System.out.println("Descricao da resposta: " + response.description());
                System.out.println("Codigo de erro: " + response.errorCode());
            }
            Util.esperar(5000);
        }
        System.out.println("Houve um problema ao enviar a resposta");
    }
}

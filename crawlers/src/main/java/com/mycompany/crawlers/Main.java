/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers;

import com.mycompany.crawlers.consumidor.ConsumidorRedditSelenium;
import com.mycompany.crawlers.consumidor.IConsumidorReddit;
import com.mycompany.crawlers.exceptions.NotificacaoException;
import com.mycompany.crawlers.notificador.INotificador;
import com.mycompany.crawlers.notificador.NotificadorTelegram;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rafao
 */
public class Main {
    public static final String DEFAULT_LIST = "askreddit;worldnews;cats";
    public static final String LIST_DELIMITER = ";";
    public static final String COMANDO_NOTIFICACAO = "/nadaprafazer";
        
    public static void main(String[] args) {
        boolean deveNotificar = false;
        String lista;
        if(args.length == 1){
            lista = args[0];
        }
        else if(args.length == 2 && args[0].toLowerCase().equals(COMANDO_NOTIFICACAO)){
            lista = args[1];
            deveNotificar = true;
        }
        else{
            lista = DEFAULT_LIST;
        }
        
        List<String> subRedditsString = Arrays.asList(lista.split(LIST_DELIMITER));
        IConsumidorReddit consumidorReddit = new ConsumidorRedditSelenium();
        String threadsEmAlta = consumidorReddit.coletarThreadsEmAlta(subRedditsString);
        System.out.println(threadsEmAlta);
        consumidorReddit.fecharConexao();
        if(deveNotificar){
            INotificador notificador = new NotificadorTelegram();
            try {
                notificador.enviarMensagem(threadsEmAlta);
            } catch (NotificacaoException ex) {
                System.out.println("Erro ao notificar. Detalhes: " + ex.getLocalizedMessage());
            }
        }
    }
}

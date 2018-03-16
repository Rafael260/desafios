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
import com.mycompany.crawlers.notificador.NotificadorTelegramAPI;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafao
 */
public class Main {

    public static void main(String[] args) {
        //Se passar a lista, faz a busca
        if(args.length == 1){
            IConsumidorReddit consumidor = new ConsumidorRedditSelenium();
            List<String> threadsEmAlta = consumidor.coletarThreadsEmAlta(args[0]);
            imprimirThreadsEmAlta(threadsEmAlta);
        }
        else if (args.length > 1){
            System.out.println("Numero errado de parâmetros. Passe apenas uma string com os subreddits separados"
                    + "por ponto e virgula, ou execute sem parâmetros para que o robô fique escutando os comandos");
        }
        //Senao, deixa o robô escutando os comandos
        else{
            iniciarRobo();
        }
    }
    
    private static void imprimirThreadsEmAlta(List<String> threadsEmAlta){
        if(!threadsEmAlta.isEmpty()){
            for(String thread: threadsEmAlta){
                System.out.println(thread);
                System.out.println("==============================================");
            }
        }
        else{
            System.out.println("Não foi encontrada nenhuma thread relevante");
        }
    }
    
    private static void iniciarRobo(){
        INotificador notificador = new NotificadorTelegramAPI();
            try {
                notificador.esperarComandos();
            } catch (NotificacaoException ex) {
                System.out.println("Houve um erro ao habilitar o robô! " + ex.getLocalizedMessage());
            }
    }
}

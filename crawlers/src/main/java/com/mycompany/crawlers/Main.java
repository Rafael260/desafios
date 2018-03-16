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
import com.mycompany.crawlers.notificador.NotificadorTelegramAPI;
import com.mycompany.crawlers.util.Util;
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
        NotificadorTelegramAPI notificador = new NotificadorTelegramAPI();
        while (true) {
            Util.esperar(10000);
        }
    }
}

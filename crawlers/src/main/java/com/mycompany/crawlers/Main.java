/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers;

import com.mycompany.crawlers.consumidor.ConsumidorRedditSelenium;
import com.mycompany.crawlers.consumidor.IConsumidorReddit;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rafao
 */
public class Main {
    
    public static void main(String[] args) {
        final String DEFAULT_LIST = "askreddit;worldnews;cats";
        final String LIST_DELIMITER = ";";
        
        String lista;
        if(args.length == 1){
            lista = args[0];
        }
        else{
            lista = DEFAULT_LIST;
        }
        
        List<String> subRedditsString = Arrays.asList(lista.split(LIST_DELIMITER));
        IConsumidorReddit consumidorReddit = new ConsumidorRedditSelenium();
        String threadsEmAlta = consumidorReddit.coletarThreadsEmAlta(subRedditsString);
        System.out.println(threadsEmAlta);
        consumidorReddit.fecharConexao();
    }
}

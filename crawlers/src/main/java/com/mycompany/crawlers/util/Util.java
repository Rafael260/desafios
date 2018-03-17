/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.util;

import com.mycompany.crawlers.model.SubReddit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafao
 */
public class Util {
    
    public static List<SubReddit> StringToSubRedditList(List<String> subRedditsString){
        List<SubReddit> subReddits = new ArrayList<>();
        SubReddit subReddit;
        for(String subRedditString: subRedditsString){
            subReddit = new SubReddit();
            subReddit.setNome(subRedditString);
            subReddits.add(subReddit);
        }
        return subReddits;
    }
    
    public static void esperar(int milisegundos){
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.consumidor;

/**
 *
 * @author rafao
 */
public class ConsumidorFactory {
    
    private String instancia;
    
    public ConsumidorFactory(){
        this.instancia = "jsoup";
    }
    
    public ConsumidorTemplate createConsumidor(){
        switch(this.instancia){
            case "jsoup":
                return new ConsumidorJSoupParser();
            case "selenium":
                return new ConsumidorSeleniumParser();
            default:
                return null;
        }
    }
    
    public ConsumidorTemplate createConsumidor(int pontuacaoMinima){
        switch(this.instancia){
            case "jsoup":
                return new ConsumidorJSoupParser(pontuacaoMinima);
            case "selenium":
                return new ConsumidorSeleniumParser(pontuacaoMinima);
            default:
                return null;
        }
    }
}

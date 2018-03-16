/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.consumidor;

import com.mycompany.crawlers.exceptions.SubRedditNaoEncontradaException;
import com.mycompany.crawlers.exceptions.ThreadIrrelevanteException;
import com.mycompany.crawlers.model.RedditThread;
import com.mycompany.crawlers.model.SubReddit;
import com.mycompany.crawlers.util.Crawler;
import com.mycompany.crawlers.util.Util;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 *
 * @author rafao
 */
public class ConsumidorRedditSelenium extends Crawler implements IConsumidorReddit {

    public static final String BASE_URL = "https://www.reddit.com/r/";
    //Se colocar a opcao top o site ja traz as threads ordenadas pelas pontuacoes
    public static final String URL_SUFIX = "/top";
    public static final int PONTUACAO_MINIMA = 5000;

    @Override
    public List<String> coletarThreadsEmAlta(List<String> subRedditsString) {
        List<SubReddit> subReddits = Util.StringToSubRedditList(subRedditsString);
        for (SubReddit subReddit : subReddits) {
            try{
                acessarSubReddit(subReddit);
                procurarThreads(subReddit);
            }catch(SubRedditNaoEncontradaException e){
                System.out.println(e.getLocalizedMessage());
            }
        }
        fecharConexao();
        return coletarRelatorioThreadsEmAlta(subReddits);
    }

    private void acessarSubReddit(SubReddit subReddit) throws SubRedditNaoEncontradaException{
        driver.get(BASE_URL + subReddit.getNome() + URL_SUFIX);
        Util.esperar(3000);
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("siteTable")));
        if(driver.findElement(By.id("siteTable")).getAttribute("innerHTML").contains("parece que não há nada aqui")){
            throw new SubRedditNaoEncontradaException("Não existe o subreddit "+ subReddit.getNome());
        }
    }

    private void procurarThreads(SubReddit subReddit) {
        WebElement tabelaThreads;
        do {
            tabelaThreads = driver.findElement(By.id("siteTable"));
            List<WebElement> divsDaTabelaDeThreads = tabelaThreads.findElements(By.tagName("div"));
            if(divsDaTabelaDeThreads.isEmpty()){
                System.out.println("Subreddit " + subReddit.getNome() + " não possui threads");
                return;
            }
            for (WebElement divDaTabela : divsDaTabelaDeThreads) {
                if(divDaTabela.getAttribute("innerHTML").contains("<p class=\"parent\">")){
                    try {
                        adicionarThreadEmAlta(subReddit, divDaTabela);
                    } catch (ThreadIrrelevanteException ex) {
                        System.out.println(ex.getMessage());
                        return;
                    }
                }
            }
            clicarNoBotaoProximo();
        } while (possuiBotaoDeProximo());
    }
    
    private boolean possuiBotaoDeProximo(){
        try{
            return driver.findElement(By.className("next-button")) != null;
        }
        catch(Exception e){
            return false;
        }
    }
    
    private void adicionarThreadEmAlta(SubReddit subReddit, WebElement divDaThread) throws ThreadIrrelevanteException{
        //Buscando apenas as divs filhas da div da thread
        List<WebElement> divsInternas = divDaThread.findElements(By.xpath("//div[@id='" + divDaThread.getAttribute("id")+"']/div"));
        String strPontuacao = divsInternas.get(0).findElements(By.tagName("div")).get(2).getAttribute("title");
        RedditThread thread = new RedditThread();
        int pontuacao = Integer.parseInt(strPontuacao);
        if(pontuacao < PONTUACAO_MINIMA){
            throw new ThreadIrrelevanteException("A thread possui pontuação inferior a " + PONTUACAO_MINIMA);
        }
        thread.setPontuacao(pontuacao);
        WebElement divInfoThread = divsInternas.get(1).findElement(By.tagName("div"));
        WebElement tituloThread = divInfoThread.findElement(By.tagName("p")).findElement(By.tagName("a"));
        thread.setTitulo(tituloThread.getAttribute("innerHTML"));
        thread.setLinkDaThread(tituloThread.getAttribute("href"));
        thread.setLinkParaComentarios(divInfoThread.findElement(By.tagName("ul")).findElement(By.tagName("li"))
                .findElement(By.tagName("a")).getAttribute("href"));
        subReddit.adicionarThread(thread);
    }

    private void clicarNoBotaoProximo() {
        try{
            WebElement botaoProximo = driver.findElement(By.className("next-button"));
            botaoProximo.click();
            waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-img")));
            Util.esperar(2000);
        }catch(Exception e){
            System.out.println("Não tem (mais) botão próximo");
        }
    }

    private List<String> coletarRelatorioThreadsEmAlta(List<SubReddit> subReddits) {
        List<String> threadsEmAlta = new ArrayList<>();
        StringBuilder sbThreadEmAlta;
        for(SubReddit subReddit: subReddits){
            if(!subReddit.getThreads().isEmpty()){
                for(RedditThread thread: subReddit.getThreads()){
                    sbThreadEmAlta = new StringBuilder();
                    sbThreadEmAlta.append("Nome da subreddit: ").append(subReddit.getNome()).append("\n");
                    sbThreadEmAlta.append(thread.toString());
                    threadsEmAlta.add(sbThreadEmAlta.toString());
                }
            }
        }
        return threadsEmAlta;
    }
}

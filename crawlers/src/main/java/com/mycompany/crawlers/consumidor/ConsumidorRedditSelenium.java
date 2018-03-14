/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.consumidor;

import com.mycompany.crawlers.exceptions.ThreadIrrelevanteException;
import com.mycompany.crawlers.model.RedditThread;
import com.mycompany.crawlers.model.SubReddit;
import com.mycompany.crawlers.util.Util;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author rafao
 */
public class ConsumidorRedditSelenium implements IConsumidorReddit {

    public static final String BASE_URL = "https://www.reddit.com/r/";
    //Se colocar a opcao top o site ja traz as threads ordenadas pelas pontuacoes
    public static final String URL_SUFIX = "/top";
    public static final int PONTUACAO_MINIMA = 5000;

    private WebDriver driver;
    private WebDriverWait waiter;

    public ConsumidorRedditSelenium() {
        ClassLoader classLoader = getClass().getClassLoader();
        String arquivoDriver = classLoader.getResource("chromedriver.exe").getFile();
        System.setProperty("webdriver.chrome.driver", arquivoDriver);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        waiter = new WebDriverWait(driver, 30);
    }

    @Override
    public String coletarThreadsEmAlta(List<String> subRedditsString) {
        List<SubReddit> subReddits = Util.StringToSubRedditList(subRedditsString);
        for (SubReddit subReddit : subReddits) {
            acessarSubReddit(subReddit);
            procurarThreads(subReddit);
        }
        return coletarRelatorioThreadsEmAlta(subReddits);
    }

    @Override
    public void fecharConexao() {
        driver.close();
    }

    private void acessarSubReddit(SubReddit subReddit) {
        driver.get(BASE_URL + subReddit.getNome() + URL_SUFIX);
        Util.esperar(3000);
    }

    private void procurarThreads(SubReddit subReddit) {
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("siteTable")));
        WebElement tabelaThreads;
        do {
            tabelaThreads = driver.findElement(By.id("siteTable"));
            List<WebElement> divsDaTabelaDeThreads = tabelaThreads.findElements(By.tagName("div"));
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
        } while (driver.findElement(By.className("next-button")) != null);

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
        WebElement botaoProximo = driver.findElement(By.className("next-button"));
        if (botaoProximo != null) {
            botaoProximo.click();
            waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-img")));
            Util.esperar(2000);
        }
    }

    private String coletarRelatorioThreadsEmAlta(List<SubReddit> subReddits) {
        StringBuilder sbRelatorio = new StringBuilder();
        for(SubReddit subReddit: subReddits){
            if(!subReddit.getThreads().isEmpty()){
                for(RedditThread thread: subReddit.getThreads()){
                    sbRelatorio.append("Nome da subreddit: ").append(subReddit.getNome()).append("\n");
                    sbRelatorio.append(thread.toString());
                    sbRelatorio.append("============================================================\n");
                }
            }
        }
        return sbRelatorio.toString();
    }

}

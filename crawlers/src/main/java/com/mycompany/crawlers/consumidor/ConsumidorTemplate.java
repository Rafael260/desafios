/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.consumidor;

import com.mycompany.crawlers.exceptions.SubRedditNaoEncontradaException;
import com.mycompany.crawlers.model.RedditThread;
import com.mycompany.crawlers.model.SubReddit;
import com.mycompany.crawlers.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author rafao
 */
public abstract class ConsumidorTemplate {
    public static final String LIST_DELIMITER = ";";
    public static final String BASE_URL = "https://www.reddit.com/r/";
    //Se colocar a opcao top o site ja traz as threads ordenadas pelas pontuacoes
    public static final String URL_SUFIX = "/top";
    public static final int PONTUACAO_MINIMA_DEFAULT = 5000;
    
    protected int pontuacaoMinima;
    
    protected WebDriver driver;
    protected WebDriverWait waiter;
    
    public ConsumidorTemplate(){
        this.pontuacaoMinima = PONTUACAO_MINIMA_DEFAULT;
        configurarDriver();
    }
    
    public ConsumidorTemplate(int pontuacao){
        if(pontuacao > 0){
            this.pontuacaoMinima = pontuacao;
        }
        else{
            this.pontuacaoMinima = PONTUACAO_MINIMA_DEFAULT;
        }
        configurarDriver();
    }
    
    protected final void configurarDriver(){
        String arquivoDriver = criarArquivoDriverNoDiretorio();
        System.setProperty("webdriver.chrome.driver", arquivoDriver);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        waiter = new WebDriverWait(driver, 30);
    }
    
    protected final String criarArquivoDriverNoDiretorio() {
        String nomeDriver = "chromedriver.exe";
        File destination = new File(nomeDriver);
        if (!destination.exists()) {
            InputStream resourceAsStream = getClass().getResourceAsStream("/chromedriver.exe");
            try {
                FileUtils.copyInputStreamToFile(resourceAsStream, destination);
            } catch (IOException ex) {
                System.out.println("Houve um erro ao fazer a copia do driver: " + ex.getLocalizedMessage());
                System.exit(-1);
            }
        }
        return nomeDriver;
    }
    
    public List<String> coletarThreadsEmAlta(String subRedditsString) {
        List<String> subRedditList = Arrays.asList(subRedditsString.split(LIST_DELIMITER));
        List<SubReddit> subReddits = Util.StringToSubRedditList(subRedditList);
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
    
    /**
     * Abre a página do subreddit, contendo a lista de threads
     * @param subReddit
     * @throws SubRedditNaoEncontradaException 
     */
    protected void acessarSubReddit(SubReddit subReddit) throws SubRedditNaoEncontradaException{
        driver.get(BASE_URL + subReddit.getNome() + URL_SUFIX);
        try{
            waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("siteTable")));
        }catch(TimeoutException e){
            throw new SubRedditNaoEncontradaException("Não existe o subreddit "+ subReddit.getNome() + " ou não é público");
        }
        if(driver.findElement(By.id("siteTable")).getAttribute("innerHTML").contains("parece que não há nada aqui")){
            throw new SubRedditNaoEncontradaException("Não existe o subreddit "+ subReddit.getNome() + 
                    " ou não possui threads recentes e relevantes");
        }
    }
    
    /**
     * Procura a lista de threads com o num de upvotes maiores que a variavel pontuacaoMinima
     * Percorre cada objeto SubReddit, e adiciona os elementos RedditThread na lista do SubReddit
     * @param subReddit 
     */
    abstract protected void procurarThreads(SubReddit subReddit);
    
    protected void fecharConexao() {
        driver.quit();
    }
    
    protected List<String> coletarRelatorioThreadsEmAlta(List<SubReddit> subReddits) {
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
    
    protected boolean possuiBotaoDeProximo(){
        try{
            return driver.findElement(By.className("next-button")) != null;
        }
        catch(Exception e){
            return false;
        }
    }
    
    protected void clicarNoBotaoProximo() {
        try{
            WebElement botaoProximo = driver.findElement(By.className("next-button"));
            botaoProximo.click();
            waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-img")));
            Util.esperar(2000);
        }catch(Exception e){
            System.out.println("Não tem (mais) botão próximo");
        }
    }
}

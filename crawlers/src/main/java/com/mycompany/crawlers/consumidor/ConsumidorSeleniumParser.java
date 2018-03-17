/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.consumidor;

import static com.mycompany.crawlers.consumidor.ConsumidorTemplate.BASE_URL;
import com.mycompany.crawlers.exceptions.ThreadIrrelevanteException;
import com.mycompany.crawlers.model.RedditThread;
import com.mycompany.crawlers.model.SubReddit;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 *
 * @author rafao
 */
public class ConsumidorSeleniumParser extends ConsumidorTemplate {

    public ConsumidorSeleniumParser(){
        super();
    }
    
    public ConsumidorSeleniumParser(int pontuacao){
        super(pontuacao);
    }
    
    @Override
    protected void procurarThreads(SubReddit subReddit) {
        List<WebElement> divsThreads;
        do {
            divsThreads = driver.findElements(By.xpath("//div[@data-context='listing']"));
            if (divsThreads == null || divsThreads.isEmpty()) {
                System.out.println("Subreddit " + subReddit.getNome() + " não existe ou não possui threads");
                return;
            }
            for (WebElement divDaThread : divsThreads) {
                try {
                    adicionarThreadEmAlta(subReddit, divDaThread);
                } catch (ThreadIrrelevanteException ex) {
                    System.out.println(ex.getLocalizedMessage());
                    return;
                }
            }
            clicarNoBotaoProximo();
        }while(possuiBotaoDeProximo());
    }

    private void adicionarThreadEmAlta(SubReddit subReddit, WebElement divDaThread) throws ThreadIrrelevanteException {
        RedditThread thread = new RedditThread();
        thread.setTitulo(divDaThread.findElement(By.cssSelector("a.title")).getText());
        thread.setPontuacao(coletarPontuacao(divDaThread));
        if (thread.getPontuacao() < this.pontuacaoMinima) {
            throw new ThreadIrrelevanteException("Thread atual possui pontuação menor que " + this.pontuacaoMinima);
        }
        thread.setLinkDaThread(coletarLink(divDaThread, "data-url"));
        thread.setLinkParaComentarios(coletarLink(divDaThread, "data-permalink"));
        subReddit.adicionarThread(thread);
    }
    
    public int coletarPontuacao(WebElement divDaThread){
        return Integer.parseInt(divDaThread.findElement(By.cssSelector("div.score.likes")).getAttribute("title"));
    }

    private String coletarLink(WebElement divDaThread, String atributoDaDiv) {
        String link = divDaThread.getAttribute(atributoDaDiv);
        if (link.startsWith("/r")) {
            link = BASE_URL.substring(0, BASE_URL.length() - 3) + link;
        }
        return link;
    }
}

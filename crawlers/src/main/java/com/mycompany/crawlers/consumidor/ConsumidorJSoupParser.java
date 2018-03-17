/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.consumidor;

import com.mycompany.crawlers.exceptions.ThreadIrrelevanteException;
import com.mycompany.crawlers.model.RedditThread;
import com.mycompany.crawlers.model.SubReddit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author rafao
 */
public class ConsumidorJSoupParser extends ConsumidorTemplate {

    private Document doc;

    public ConsumidorJSoupParser() {
        super();
    }

    public ConsumidorJSoupParser(int pontuacao) {
        super(pontuacao);
    }

    @Override
    protected void procurarThreads(SubReddit subReddit) {
        do {
            doc = Jsoup.parse(driver.getPageSource());
            Elements threads = doc.select("div.thing[data-context=listing]");
            if (threads == null || threads.isEmpty()) {
                System.out.println("Subreddit " + subReddit.getNome() + " não possui threads");
                return;
            }
            for (Element element : threads) {
                try{
                    adicionarThreadEmAlta(subReddit, element);
                } catch (ThreadIrrelevanteException ex) {
                    System.out.println(ex.getLocalizedMessage());
                    System.out.println("Indo para o próximo subreddit");
                    return;
                }
            }
            clicarNoBotaoProximo();
        } while (possuiBotaoDeProximo());
    }

    private void adicionarThreadEmAlta(SubReddit subReddit, Element element) throws ThreadIrrelevanteException{
        RedditThread thread = new RedditThread();
        thread.setTitulo(element.select("a.title").first().text());
        thread.setPontuacao(coletarPontuacao(element));
        if (thread.getPontuacao() < this.pontuacaoMinima) {
            throw new ThreadIrrelevanteException("Thread atual possui pontuação menor que " + this.pontuacaoMinima);
        }
        thread.setLinkDaThread(coletarLinkDaThread(element));
        thread.setLinkParaComentarios(element.select("a.bylink.comments").first().attr("href"));
        subReddit.adicionarThread(thread);
    }
    
    private int coletarPontuacao(Element element){
        return Integer.parseInt(element.select("div.score.likes").first().attr("title"));
    }
    
    private String coletarLinkDaThread(Element element){
        String link = element.attr("data-url");
        if(link.startsWith("/r")){
            link = BASE_URL.substring(0, BASE_URL.length() - 3) + link;
        }
        return link;
    }
    
}

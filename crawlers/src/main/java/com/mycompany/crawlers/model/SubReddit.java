/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafao
 */
public class SubReddit {
    private String nome;
    private List<RedditThread> threads;
    
    public SubReddit(){
        threads = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<RedditThread> getThreads() {
        return threads;
    }
    
    public void adicionarThread(RedditThread thread){
        this.threads.add(thread);
    }
}

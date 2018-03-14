/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.model;

/**
 *
 * @author rafao
 */
public class RedditThread {
    private String titulo;
    private Integer pontuacao;
    private String linkParaComentarios;
    private String linkDaThread;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getLinkParaComentarios() {
        return linkParaComentarios;
    }

    public void setLinkParaComentarios(String linkParaComentarios) {
        this.linkParaComentarios = linkParaComentarios;
    }

    public String getLinkDaThread() {
        return linkDaThread;
    }

    public void setLinkDaThread(String linkDaThread) {
        this.linkDaThread = linkDaThread;
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Título da thread: ").append(this.titulo).append("\n");
        str.append("Upvotes: ").append(this.pontuacao).append("\n");
        str.append("Link da thread: ").append(this.linkDaThread).append("\n");
        str.append("Link dos comentários: ").append(this.linkParaComentarios).append("\n");
        return str.toString();
    }
}

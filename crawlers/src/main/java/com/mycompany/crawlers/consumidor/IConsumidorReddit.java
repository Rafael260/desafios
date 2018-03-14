package com.mycompany.crawlers.consumidor;


import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rafao
 */
public interface IConsumidorReddit {
    
    public String coletarThreadsEmAlta(List<String> subReddits);
    public void fecharConexao();
}

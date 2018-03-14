/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.notificador;

import com.mycompany.crawlers.exceptions.NotificacaoException;

/**
 *
 * @author rafao
 */
public interface INotificador {
    
    public void enviarMensagem(String str) throws NotificacaoException;
}

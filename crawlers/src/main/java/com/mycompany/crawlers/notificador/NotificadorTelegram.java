/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.notificador;

import com.mycompany.crawlers.exceptions.NotificacaoException;
import com.mycompany.crawlers.util.Crawler;
import com.mycompany.crawlers.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 *
 * @author rafao
 */
public class NotificadorTelegram extends Crawler implements INotificador{

    public static final String CODIGO_BRASIL = "+55";
    public static final String DESTINATARIO_DEFAULT = "11948365094";
    public static final String URL_TELEGRAM_WEB = "https://web.telegram.org/";
    
    public NotificadorTelegram(){
    }
            
    
    @Override
    public void enviarMensagem(String str) throws NotificacaoException {
        acessarTelegramWeb();
        digitarNumeroNaBuscaDeConversas(CODIGO_BRASIL+DESTINATARIO_DEFAULT);
        Util.esperar(1000);
        selecionarConversaParaEnvio();
        Util.esperar(1000);
        colarListaNaConversa(str);
        Util.esperar(3000);
        fecharConexao();
    }
    
    private void acessarTelegramWeb(){
        driver.get(URL_TELEGRAM_WEB);
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.className("icon-hamburger-wrap")));
        Util.esperar(1000);
    }
    
    private void digitarNumeroNaBuscaDeConversas(String numero){
        WebElement campoBuscaConversas = driver.findElement(By.className("im_dialogs_search")).findElement(By.tagName("input"));
        campoBuscaConversas.sendKeys(numero);
    }
    
    private void selecionarConversaParaEnvio(){
        WebElement conversa = driver.findElement(By.xpath("//div[@class='im_dialogs_col']/div/div/ul/li"));
        conversa.click();
        Util.esperar(1000);
    }
    
    private void colarListaNaConversa(String lista){
        WebElement caixaDeTexto = driver.findElement(By.className("composer_rich_textarea"));
        caixaDeTexto.sendKeys(lista);
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crawlers.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author rafao
 */
public abstract class Crawler {

    protected WebDriver driver;
    protected WebDriverWait waiter;

    public Crawler() {
        String arquivoDriver = criarArquivoDriverNoDiretorio();
        System.setProperty("webdriver.chrome.driver", arquivoDriver);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        waiter = new WebDriverWait(driver, 30);
    }

    private final String criarArquivoDriverNoDiretorio() {
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

    public void fecharConexao() {
        driver.quit();
    }
}

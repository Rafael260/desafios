/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idwall.desafio.test;

import idwall.desafio.exceptions.LimiteBaixoException;
import idwall.desafio.string.IdwallFormatter;
import idwall.desafio.string.StringFormatter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafao
 */
public class IdwallFormatterTest {

    private int limiteParaTeste = 20;
    private StringFormatter formatter;
    private String texto = "a aa aaa aaaa aaaaa aaaaaa aaaaaa aa aaaa aaa aaaaaa aaa aaaaa aaa aaaa aaaa";

    public IdwallFormatterTest() {
    }

    @Before
    public void setUp() {
        formatter = new IdwallFormatter(limiteParaTeste);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testFormatarHappyPath() throws LimiteBaixoException {
        String textoFormatado = formatter.format(texto, false);
        String[] linhasDoTexto = textoFormatado.split("\n");
        for (String linhaDoTexto : linhasDoTexto) {
            assertTrue(linhaDoTexto.length() <= limiteParaTeste);
        }
    }
    
    @Test
    public void testFormatarJustificadoHappyPath() throws LimiteBaixoException {
        String textoFormatado = formatter.format(texto, true);
        String[] linhasDoTexto = textoFormatado.split("\n");
        for (String linhaDoTexto : linhasDoTexto) {
            assertTrue(linhaDoTexto.length() == limiteParaTeste);
        }
    }
    
    @Test(expected = LimiteBaixoException.class)
    public void testFormatarComLimiteBaixo() throws LimiteBaixoException{
        String textoPraFormatar = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        int limite = 12;
        formatter = new IdwallFormatter(limite);
        formatter.format(textoPraFormatar, true);
    }
}

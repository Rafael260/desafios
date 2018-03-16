/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idwall.desafio.test;

import idwall.desafio.exceptions.EntradaInvalidaException;
import idwall.desafio.validations.StringFormatterValidator;
import org.junit.Test;

/**
 *
 * @author rafao
 */
public class StringFormatterValidatorTest {
    
    public StringFormatterValidatorTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testEntradaHappyPath() throws EntradaInvalidaException {
        String[] args = new String[3];
        args[0] = "Teste de texto happy path";
        args[1] = "20";
        args[2] = "true";
        StringFormatterValidator.validarCampos(args);
    }
    
    @Test(expected = EntradaInvalidaException.class)
    public void testEntradaTextoInvalido() throws EntradaInvalidaException {
        String[] args = new String[3];
        args[0] = "";
        args[1] = "20";
        args[2] = "true";
        StringFormatterValidator.validarCampos(args);
    }
    
    @Test(expected = EntradaInvalidaException.class)
    public void testEntradaLimiteInvalido() throws EntradaInvalidaException {
        String[] args = new String[3];
        args[0] = "Teste de texto Um teste de texto";
        args[1] = "20s";
        args[2] = "true";
        StringFormatterValidator.validarCampos(args);
    }
    
    @Test(expected = EntradaInvalidaException.class)
    public void testEntradaBooleanoInvalido() throws EntradaInvalidaException {
        String[] args = new String[3];
        args[0] = "Teste de texto Um teste de texto";
        args[1] = "20";
        args[2] = "yes";
        StringFormatterValidator.validarCampos(args);
    }
}

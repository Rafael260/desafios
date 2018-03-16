/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idwall.desafio.validations;

import idwall.desafio.exceptions.EntradaInvalidaException;

/**
 *
 * @author rafao
 */
public class StringFormatterValidator {

    public static final int LIMITE_MINIMO = 10;
    
    public static void validarCampos(String[] args) throws EntradaInvalidaException {
        StringBuilder mensagemErro = new StringBuilder();
        if (args.length >= 1 && args[0].isEmpty()) {
            mensagemErro.append("Texto inválido").append("\n");
        }
        if(args.length >= 2){
            Integer limit = LIMITE_MINIMO;
            try{
                limit = Integer.parseInt(args[1]);
            }catch(NumberFormatException e){
                mensagemErro.append("O segundo parâmetro deve ser um número inteiro").append("\n");
            }
            if(limit < LIMITE_MINIMO){
                mensagemErro.append("O limite mínimo para a aplicação é " + LIMITE_MINIMO).append("\n");
            }
        }
        if(args.length >= 3){
            if(args[2].isEmpty() || (!args[2].equalsIgnoreCase("true") && !args[2].equalsIgnoreCase("false")) ){
                mensagemErro.append("Parâmetro para justificar está inválido. Utilize \"true\" ou \"false\" ").append("\n");
            }
        }
        if(!mensagemErro.toString().isEmpty()){
            throw new EntradaInvalidaException(mensagemErro.toString());
        }
    }
}

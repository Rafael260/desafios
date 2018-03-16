package idwall.desafio;

import idwall.desafio.exceptions.EntradaInvalidaException;
import idwall.desafio.exceptions.LimiteBaixoException;
import idwall.desafio.string.IdwallFormatter;
import idwall.desafio.string.StringFormatter;
import idwall.desafio.validations.StringFormatterValidator;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public class Main {

    private static final String DEFAULT_INPUT_TEXT = "In the beginning God created the heavens and the earth. Now the earth was formless and empty, darkness was over the surface of the deep, and the Spirit of God was hovering over the waters.\n"
            + "\n"
            + "And God said, \"Let there be light,\" and there was light. God saw that the light was good, and he separated the light from the darkness. God called the light \"day,\" and the darkness he called \"night.\" And there was evening, and there was morning - the first day.";
    private static final Integer DEFAULT_LIMIT = 40;
    private static final Boolean DEFAULT_JUSTIFY = true;

    public static void main(String[] args) {
        String text = DEFAULT_INPUT_TEXT;
        Integer limit = DEFAULT_LIMIT;
        Boolean justify = DEFAULT_JUSTIFY;
        
        try{
            StringFormatterValidator.validarCampos(args);
        }catch(EntradaInvalidaException e){
            System.out.println(e.getLocalizedMessage());
            System.exit(-1);
        }
        if(args.length >= 1){
            text = args[0];
        }
        if(args.length >= 2){
            limit = Integer.parseInt(args[1]);
        }
        if(args.length >= 3){
            justify = Boolean.parseBoolean(args[2]);
        }
        // Print input data
        System.out.println("Inputs: ");
        System.out.println("Text: " + text);
        System.out.println("Limit: " + limit);
        System.out.println("Should justify: " + justify);
        System.out.println("=========================");

        // Run IdwallFormatter
        final StringFormatter sf = new IdwallFormatter(limit);
        String outputText;
        try {
            outputText = sf.format(text, justify);
            // Print output text
            System.out.println("Output: ");
            System.out.println(outputText);
        } catch (LimiteBaixoException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}

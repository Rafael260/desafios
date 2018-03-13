package idwall.desafio.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rodrigo CatÃ£o Araujo on 06/02/2018.
 */
public class IdwallFormatter extends StringFormatter {

    public IdwallFormatter(){
        super();
    }
    
    public IdwallFormatter(Integer limit){
        super(limit);
    }
    
    /**
     * Should format as described in the challenge
     *
     * @param text
     * @return
     */
    @Override
    public String format(String text) {
        List<String> linhasDoTextoOriginal = Arrays.asList(text.split("\n"));
        String textoFormatado = "";
        for(String linhaDoOriginal: linhasDoTextoOriginal){
            textoFormatado += coletarLinhaFormatada(linhaDoOriginal);
        }
        return textoFormatado;
    }
    
    /**
     * Recebe uma linha do texto original e retorna a linha formatada de acordo com o limite de caracteres
     * @param linhaOriginal
     * @return 
     */
    private String coletarLinhaFormatada(String linhaOriginal){
        List<String> palavras = Arrays.asList(linhaOriginal.split(" "));
        Iterator<String> palavraIterator = palavras.iterator();
        String palavraAtual, linhaAtual = "", textoFormatado = "";
        
        while(palavraIterator.hasNext()){
            palavraAtual = palavraIterator.next();
            if (!palavraCabeNaLinha(palavraAtual, linhaAtual)){
                textoFormatado = acrescentarLinhaNoTextoFormatado(linhaAtual, textoFormatado);
                linhaAtual = "";
            }
            linhaAtual = acrescentarPalavraNaLinha(palavraAtual, linhaAtual);
        }
        //Acrescenta a última linha formatada
        textoFormatado = acrescentarLinhaNoTextoFormatado(linhaAtual, textoFormatado);
        return textoFormatado;
    }

    private String acrescentarLinhaNoTextoFormatado(String linha, String textoFormatado){
        return textoFormatado + linha + "\n";
    }
    
    /**
     * Coloca a palavra na linha atual
     * @param palavraAtual
     * @param linhaAtual
     * @return 
     */
    private String acrescentarPalavraNaLinha(String palavraAtual, String linhaAtual){
        if(!linhaAtual.isEmpty()){
            linhaAtual += " ";
        }
        linhaAtual += palavraAtual;
        return linhaAtual;
    }
    
    /**
     * Testa se é possível colocar mais uma palavra na mesma linha, respeitando o limite de caracteres
     * @param palavra
     * @param linha
     * @return 
     */
    private boolean palavraCabeNaLinha(String palavra, String linha){
        return linha.length() + palavra.length() < this.limit;
    }
    
}

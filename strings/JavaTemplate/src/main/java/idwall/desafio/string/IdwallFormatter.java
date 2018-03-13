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
    public String format(String text, boolean justify) {
        List<String> linhasDoTextoOriginal = Arrays.asList(text.split("\n"));
        String textoFormatado = "";
        for(String linhaDoOriginal: linhasDoTextoOriginal){
            textoFormatado += coletarLinhaFormatada(linhaDoOriginal, justify);
        }
        return textoFormatado;
    }
    
    /**
     * Recebe uma linha do texto original e retorna a linha formatada de acordo com o limite de caracteres
     * @param linhaOriginal
     * @return 
     */
    private String coletarLinhaFormatada(String linhaOriginal, boolean justificar){
        List<String> palavras = Arrays.asList(linhaOriginal.split(" "));
        Iterator<String> palavraIterator = palavras.iterator();
        String palavraAtual, linhaAtual = "", textoFormatado = "";
        
        while(palavraIterator.hasNext()){
            palavraAtual = palavraIterator.next();
            if (!palavraCabeNaLinha(palavraAtual, linhaAtual)){
                textoFormatado = acrescentarLinhaNoTextoFormatado(linhaAtual, textoFormatado, justificar);
                linhaAtual = "";
            }
            linhaAtual = acrescentarPalavraNaLinha(palavraAtual, linhaAtual);
        }
        //Acrescenta a última linha formatada
        textoFormatado = acrescentarLinhaNoTextoFormatado(linhaAtual, textoFormatado, justificar);
        return textoFormatado;
    }

    private String acrescentarLinhaNoTextoFormatado(String linha, String textoFormatado, boolean justificar){
        if(justificar && !linha.isEmpty()){
            linha = justificarLinha(linha);
        }
        return textoFormatado + linha + "\n";
    }
    
    /**
     * Justifica a linha para a quantidade exata de caracteres
     * @param linha linha no máximo com nº de caracteres do limite
     * @return 
     */
    private String justificarLinha(String linha){
        String[] palavras = linha.split(" ");
        Integer numeroEspacos = palavras.length - 1;
        String[] espacos = new String[numeroEspacos];
        Arrays.fill(espacos," ");
        int numeroCaracteresUsados = linha.length();
        boolean percorrerIndicesPares = true;
        while(numeroCaracteresUsados < this.limit.intValue()){
            System.out.println("NUM CARACTERES USADOS: " + numeroCaracteresUsados);
            System.out.println("While do numeroCaracteresUsados < this.limit");
            for(int i = coletarIndiceInicial(percorrerIndicesPares); i < espacos.length; i+= 2){
                System.out.println("for do coletarIndiceInicial");
                if(numeroCaracteresUsados == this.limit){
                    System.out.println("Numero de caracteres usados eh igual ao limite de caracteres");
                    break;
                }
                espacos[i] += " ";
                numeroCaracteresUsados++;
            }
            percorrerIndicesPares = !percorrerIndicesPares;
        }
        System.out.println("Vai retornar a linha justificada!");
        return montarLinhaJustificada(palavras, espacos);
    }
    
    private String montarLinhaJustificada(String[] palavras, String[] espacos){
        StringBuilder linha = new StringBuilder();
        //Lembrando que espacos.length + 1 = palavras.length
        for(int i = 0; i < espacos.length; i++){
            linha.append(palavras[i]);
            linha.append(espacos[i]);
        }
        linha.append(palavras[palavras.length-1]);
        return linha.toString();
    }
    
    private Integer coletarIndiceInicial(boolean percorrerIndicesPares){
        return percorrerIndicesPares ? 0 : 1;
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

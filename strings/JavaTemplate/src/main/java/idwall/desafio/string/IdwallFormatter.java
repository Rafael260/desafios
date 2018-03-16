package idwall.desafio.string;

import idwall.desafio.exceptions.LimiteBaixoException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rodrigo CatÃ£o Araujo on 06/02/2018.
 */
public class IdwallFormatter extends StringFormatter {

    public IdwallFormatter() {
        super();
    }

    public IdwallFormatter(Integer limit) {
        super(limit);
    }

    /**
     * Should format as described in the challenge
     *
     * @param text
     * @return
     */
    @Override
    public String format(String text, boolean justify) throws LimiteBaixoException {
        List<String> linhasDoTextoOriginal = Arrays.asList(text.split("\n"));
        StringBuilder textoFormatado = new StringBuilder();
        for (String linhaDoOriginal : linhasDoTextoOriginal) {
            textoFormatado.append(coletarLinhaFormatada(linhaDoOriginal, justify));
        }
        return textoFormatado.toString();
    }

    /**
     * Recebe uma linha do texto original e retorna a linha formatada de acordo
     * com o limite de caracteres
     *
     * @param linhaOriginal
     * @return
     */
    private String coletarLinhaFormatada(String linhaOriginal, boolean justificar) throws LimiteBaixoException {
        List<String> palavras = Arrays.asList(linhaOriginal.split(" "));
        Iterator<String> palavraIterator = palavras.iterator();
        String palavraAtual, linhaAtual = "", textoFormatado = "";

        while (palavraIterator.hasNext()) {
            palavraAtual = palavraIterator.next();
            if (!palavraCabeNaLinha(palavraAtual, linhaAtual)) {
                if (palavraAtual.length() > this.limit) {
                    throw new LimiteBaixoException("O tamanho do limite eh menor do que o recomendado para o texto.\n"
                            + "Utilize no mínimo " + palavraAtual.length() + " de limite");
                }
                textoFormatado = acrescentarLinhaNoTextoFormatado(linhaAtual, textoFormatado, justificar);
                linhaAtual = "";
            }
            linhaAtual = acrescentarPalavraNaLinha(palavraAtual, linhaAtual);
        }
        //Acrescenta a ultima linha formatada
        textoFormatado = acrescentarLinhaNoTextoFormatado(linhaAtual, textoFormatado, justificar);
        return textoFormatado;
    }

    private String acrescentarLinhaNoTextoFormatado(String linha, String textoFormatado, boolean justificar) {
        //So justifique a linha se nao for uma linha vazia, que o boolean esteja true
        //e que a linha nao tenha apenas uma palavra
        if (!linha.isEmpty() && justificar && linha.split(" ").length > 1) {
            linha = justificarLinha(linha);
        }
        return textoFormatado + linha + "\n";
    }

    /**
     * Justifica a linha para a quantidade exata de caracteres
     *
     * @param linha linha no maximo com num de caracteres do limite
     * @return
     */
    private String justificarLinha(String linha) {
        String[] palavras = linha.split(" ");
        Integer numeroEspacos = palavras.length - 1;
        String[] espacos = new String[numeroEspacos];
        Arrays.fill(espacos, " ");
        int numeroCaracteresUsados = linha.length();
        boolean percorrerIndicesPares = true;
        while (numeroCaracteresUsados < this.limit.intValue()) {
            for (int i = coletarIndiceInicial(percorrerIndicesPares); i < espacos.length; i += 2) {
                if (numeroCaracteresUsados == this.limit) {
                    break;
                }
                espacos[i] += " ";
                numeroCaracteresUsados++;
            }
            percorrerIndicesPares = !percorrerIndicesPares;
        }
        return montarLinhaJustificada(palavras, espacos);
    }

    private String montarLinhaJustificada(String[] palavras, String[] espacos) {
        StringBuilder linha = new StringBuilder();
        //Lembrando que espacos.length + 1 = palavras.length
        for (int i = 0; i < espacos.length; i++) {
            linha.append(palavras[i]);
            linha.append(espacos[i]);
        }
        linha.append(palavras[palavras.length - 1]);
        return linha.toString();
    }

    private Integer coletarIndiceInicial(boolean percorrerIndicesPares) {
        return percorrerIndicesPares ? 0 : 1;
    }

    /**
     * Coloca a palavra na linha atual
     *
     * @param palavraAtual
     * @param linhaAtual
     * @return
     */
    private String acrescentarPalavraNaLinha(String palavraAtual, String linhaAtual) {
        if (!linhaAtual.isEmpty()) {
            linhaAtual += " ";
        }
        linhaAtual += palavraAtual;
        return linhaAtual;
    }

    /**
     * Testa se é possivel colocar mais uma palavra na mesma linha, respeitando
     * o limite de caracteres
     *
     * @param palavra
     * @param linha
     * @return
     */
    private boolean palavraCabeNaLinha(String palavra, String linha) {
        return linha.length() + palavra.length() < this.limit;
    }

}

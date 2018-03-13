package idwall.desafio.string;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public abstract class StringFormatter {

    protected Integer limit;

    public StringFormatter() {
        this.limit = 40;
    }
    
    public StringFormatter(Integer limit){
        if(limit != null){
            this.limit = limit;
        }
        else{
            this.limit = 40;
        }
    }

    /**
     * It receives a text and should format this text
     *
     * @param text
     * @return
     */
    public abstract String format(String text);
}

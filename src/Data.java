import java.time.LocalDate;
public class Data  {

    private int Anno;
    private int Mese;
    private int Giorno;

    public Data(int Anno, int Mese, int Giorno) {
        if(!DataValida(Anno, Mese, Giorno)){
            throw new IllegalArgumentException("La data non è valida");
        }
        this.Anno = Anno;
        this.Mese = Mese;
        this.Giorno = Giorno;
    }

    public int getAnno() {
        return Anno;
    }

    public int getMese() {
        return Mese;
    }

    public int getGiorno() {
        return Giorno;
    }

    //Metodo privato per stabilire se la data è valida
    private boolean DataValida(int anno, int mese, int giorno){
        try{
            LocalDate.of(anno,mese,giorno);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static Data Oggi(){
        LocalDate dataOdierna = LocalDate.now();
        return new Data(dataOdierna.getYear(), dataOdierna.getMonthValue(),dataOdierna.getDayOfMonth());
    }
    //Conversione in LocalDate
    public LocalDate toLocalDate() {
        return LocalDate.of(Anno, Mese, Giorno);
    }

    public boolean DataPassata(){
        return LocalDate.now().isAfter(this.toLocalDate());
    }

    @Override
    public String toString() {
        return + Anno + "/" + Mese + "/" + Giorno;
    }
}



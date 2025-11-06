import java.io.Serializable;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Data implements Serializable {
    private static final long serialVersionUID = 1L;
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
    public  boolean equals(Object obj) {
        // 1. Controlla se è lo stesso identico oggetto in memoria
        if (this == obj) {
            return true;
        }

        // 2. Controlla se l'oggetto è nullo o di una classe diversa
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // 3. Converte l'oggetto (che sappiamo essere di tipo Data)
        Data data = (Data) obj;

        // 4. Confronta i campi (uso i nomi del tuo file)
        return Anno == data.Anno &&
                Mese == data.Mese &&
                Giorno == data.Giorno;
    }

    @Override
    public int hashCode() {
        // Questo è importante da aggiungere quando si sovrascrive equals
        return java.util.Objects.hash(Anno, Mese, Giorno);
    }

    @Override
    public String toString() {
        return + Anno + "/" + Mese + "/" + Giorno;
    }
}



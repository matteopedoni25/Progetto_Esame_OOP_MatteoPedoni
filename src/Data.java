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

    public static Data richiediData(Scanner scanner) {
        while (true) { // Inizia un ciclo infinito
            try {
                System.out.println("Inserisci la data:");
                System.out.print("Anno (YYYY): ");
                int anno = scanner.nextInt();

                System.out.print("Mese (1-12): ");
                int mese = scanner.nextInt();

                System.out.print("Giorno (1-31): ");
                int giorno = scanner.nextInt();
                scanner.nextLine(); // Consuma il "ritorno a capo" rimasto nel buffer
                Data dataInserita = new Data(anno, mese, giorno);

                return dataInserita;

            } catch (InputMismatchException e) {
                // Errore: l'utente ha inserito testo invece di un numero
                System.out.println("ERRORE: Devi inserire solo numeri. Riprova.");
                scanner.nextLine(); // Pulisce lo scanner dall'input errato
            } catch (IllegalArgumentException e) {
                // Errore: i numeri formano una data non valida (es. 30/02/2024)
                // Questo messaggio d'errore (e.getMessage()) arriva da "La data non è valida"
                System.out.println("ERRORE: " + e.getMessage() + ". Riprova.");
            }
        }
    }

    @Override
    public  boolean equals(Object obj) {
        // Controlla se è lo stesso identico oggetto in memoria
        if (this == obj) {
            return true;
        }

        // Controlla se l'oggetto è nullo o di una classe diversa
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Converte l'oggetto (che sappiamo essere di tipo Data)
        Data data = (Data) obj;

        //Confronta i campi (uso i nomi del tuo file)
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



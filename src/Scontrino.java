import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Scontrino implements Serializable {
    private static final long serialVersionUID = 1L;
    private String targa_utente;
    private double prezzo;
    private Data dataIN;
    private Data dataOUT;
    private Orario Orario_Arrivo;
    private Orario Orario_Uscita;

    public static final double PREZZO_PER_ORA = 1.0; // Euro per ora

    public Scontrino(String targa_utente, Data data , Orario Orario_Arrivo) {
        this.targa_utente = targa_utente;
        this.prezzo = 0.0; // verrà calcolato all'uscita
        this.dataIN = Data.Oggi();
        this.dataOUT = null; // inizializzo a nulle, calcolato in uscita
        this.Orario_Arrivo = Orario_Arrivo;
        this.Orario_Uscita = null; //inzializzo a null, calcolato all'uscita
    }

    public String getTarga_utente() {
        return targa_utente;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public Data getDataIN() {
        return dataIN;
    }

    public Data getDataOUT() { return  dataOUT; }

    public Orario getOrario_Arrivo() {
        return Orario_Arrivo;
    }

    public Orario getOrario_Uscita() {
        return Orario_Uscita;
    }

    public void setUscita(Data dataOUT, Orario Orario_Uscita) {
        this.dataOUT = dataOUT;
        this.Orario_Uscita = Orario_Uscita;
    }

    public long calcola_ore() {
        if (Orario_Uscita == null) {
            return 0; // l'utente è ancora all'interno del parcheggio
        }

        LocalDateTime arrivo = LocalDateTime.of(
                this.dataIN.toLocalDate(),
                this.Orario_Arrivo.toLocalTime()
        );

        LocalDateTime Uscita= LocalDateTime.of(
                this.dataOUT.toLocalDate(),
                this.Orario_Uscita.toLocalTime()
        );

        long minuti = Duration.between(arrivo, Uscita).toMinutes();

        long ora = minuti / 60;

        if (minuti % 60 > 15){ // arrotonda per eccesso di 15 minuti, per permettere all'utente usufruire a pieno dell'ora
            ora++;
        }

        return ora;
    }

    public void calcolaPrezzo(){ //calcoliamo il prezzo che deve pagare un utente.
        long ora = calcola_ore();
        this.prezzo = ora * PREZZO_PER_ORA;
    }


    public void registraUscita( Data dataOUT, Orario Orario_Uscita){ // Riceviamo l'orario di uscita e calcoliamo il prezzo
        setUscita(dataOUT,Orario_Uscita);
        calcolaPrezzo();
    }
    public boolean Pay(){ // se l'orario d'uscita è disponibile sappiamo che lo scontrino è stato pagato
        return Orario_Uscita != null;
    }

    public String stampaIngresso() {
        return "Scontrino Ingresso: [TARGA: " + getTarga_utente() + ", DATA: " + getDataIN() +
                ", ENTRATA: " + getOrario_Arrivo() + "]";
    }

    @Override
    public String toString() {
        return "Scontrino: [TARGA: "+getTarga_utente()+", DATA: "+ getDataIN()+
                ", ENTRATA alle ORE: "+getOrario_Arrivo()+", USCITA in DATA: "+getDataOUT()+", alle ORE:"+getOrario_Uscita()+
                ", PREZZO: "+getPrezzo()+"]";
    }
}

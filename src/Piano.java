import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;;

public class Piano implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numPiano;
    private int postiTot;
    private int postiDisponibili;
    private int postiOccupati;
    private List<Scontrino> scontrini;

    //Costanti
    public static final int PIANI_TOTALI= 5;
    public static final int POSTI_X_PIANO=10;

    //Costruttore del piano
    public Piano(int numPiano, int postiTot) {
        this.numPiano = numPiano;
        this.postiTot = postiTot;
        this.postiDisponibili = postiTot;
        this.scontrini = new ArrayList<>();
    }

    //Metodi Get && Set
    public int getNumPiano() {
        return numPiano;
    }

    public int getPostiTot() {
        return postiTot;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public int getPostiOccupati() {
        return postiOccupati;
    }

    public List<Scontrino> getScontrini() {
        return scontrini;
    }

    //Metodi utili per la gestione del parcheggio lato utente
    public Scontrino registraScontrino(String targa,Data data, Orario orarioArrivo) {
        Scontrino scontrino = new Scontrino(targa,data,orarioArrivo);
        if (postiDisponibili > 0) {
            scontrini.add(scontrino);
            postiDisponibili--;
            postiOccupati++;
        } else {
            System.out.println("Nessun posto disponibile al piano " + numPiano);
        }
        return  scontrino;
    }

    public void registraUscita(Scontrino scontrino, Data dataOUT , Orario Orario_Uscita) {
        if (scontrini.contains(scontrino) && !scontrino.Pay()) {
            scontrino.registraUscita(dataOUT,Orario_Uscita);
            postiDisponibili++;
            postiOccupati--;
        }

    }

    //Stringa dati del piano
    public String toString() {
        return "[Piano: " + numPiano + ", Posti Disponibili: " + postiDisponibili +", Posti Occupati:"+postiOccupati+"]\n";
    }

}

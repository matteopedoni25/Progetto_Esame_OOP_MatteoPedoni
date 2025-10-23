public class Scontrino {
    private String targa_utente;
    private double prezzo;
    private Data data;
    private Orario Orario_Arrivo;
    private Orario Orario_Uscita;

    public static final double PREZZO_PER_ORA = 1.0; // Euro per ora

    public Scontrino(String targa_utente, Data data , Orario Orario_Arrivo) {
        this.targa_utente = targa_utente;
        this.prezzo = 0.0; // verrà calcolato all'uscita
        this.data = Data.Oggi();
        this.Orario_Arrivo = Orario_Arrivo;
        this.Orario_Uscita = null; //verrà calcolato all'uscita
    }

    public String getTarga_utente() {
        return targa_utente;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public Data getData() {
        return data;
    }


    public Orario getOrario_Arrivo() {
        return Orario_Arrivo;
    }

    public Orario getOrario_Uscita() {
        return Orario_Uscita;
    }

    public void setOrario_Uscita(Orario Orario_Uscita) {
       this.Orario_Uscita = Orario_Uscita;
    }

    public int calcola_ore() {
        if (Orario_Uscita == null) {
            return 0; // l'utente è ancora all'interno del parcheggio
        }
        int minuti = Orario_Arrivo.calcolaDifferenza(Orario_Uscita);

        int ora = minuti / 60;

        if (minuti % 60 > 15){ // arrotonda per eccesso di 15 minuti,per permettere all'utente usufruire a pieno dell'ora
            ora++;
        }

        return ora;
    }

    public void calcolaPrezzo(){ //calcoliamo il prezzo che deve pagare un utente.
        int ora = calcola_ore();
        this.prezzo = ora * PREZZO_PER_ORA;
    }


    public void registraUscita(Orario Orario_Uscita){ // Riceviamo l'orario di uscita e calcoliamo il prezzo
        setOrario_Uscita(Orario_Uscita);
        calcolaPrezzo();
    }
    public boolean Pay(){ // se l'orario d'uscita è disponibile sappiamo che lo scontrino è stato pagato
        return Orario_Uscita != null;
    }


    @Override
    public String toString() {
        return "Scontrino: [TARGA: "+getTarga_utente()+", DATA: "+getData()+
                ", ENTRATA: "+getOrario_Arrivo()+", USCITA:"+getOrario_Uscita()+
                ", PREZZO: "+getPrezzo()+"]";
    }
}

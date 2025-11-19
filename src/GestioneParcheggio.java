import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.*;

public class GestioneParcheggio implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Piano> piani; //il parcheggio avra più piani, quindi è neccessaria una lista di piani

    //Costruttore che inizializza i piani, con dei posti disponibili, del parcheggio
    public GestioneParcheggio() {
        this.piani = new ArrayList<>();
        for (int i = 0; i < Piano.PIANI_TOTALI; i++) {
            piani.add(new Piano(i,Piano.POSTI_X_PIANO));// Ogni elemento dell'ArrayList è un piano con tot posti.
        }
    }

    public List<Piano> getPiani() {
        return piani;
    }

    public Optional<Piano> PianoConPiuPosti(){ // Utilizzo Optional per creare uno stream che trova il piano con più posti
        return piani.stream()
                .filter(piano -> piano.getPostiDisponibili() > 0)
                .max((p1, p2)-> Integer.compare(p1.getPostiDisponibili(), p2.getPostiDisponibili()));

    }


    public Optional<Scontrino> RegistraIngresso(String targa){ //registro l'ingresso come metodo Optional
        Optional<Piano> piano = PianoConPiuPosti();
        if (piano.isPresent()) {
            Piano p = piano.get();
            Data data = Data.Oggi();
            Orario oraIngresso = Orario.adesso();
            Scontrino scontrino = p.registraScontrino(targa, data, oraIngresso);
            if (scontrino != null) return Optional.of(scontrino);
        }
        return Optional.empty();
    }



    public Optional<Piano> trovaPianoxTarga(String targa) {
        return piani.stream()
                // Filtra i piani: tieni solo quelli che soddisfano la condizione
                .filter(piano -> piano.getScontrini() // Per ogni piano, prendi la lista di scontrini
                        .stream() // Crea uno stream di scontrini
                        // anyMatch controlla se almeno un elemento dello stream matcha la condizione
                        .anyMatch(scontrino -> scontrino.getTarga_utente().equals(targa) && scontrino.getOrario_Uscita() == null))
                .findFirst(); // Prendi il primo piano che ha superato il filtro
    }
    public Optional<Scontrino> registraUscitaPerTarga(String targa) {
        //Trova il piano dove si trova l'auto
        Optional<Piano> pianoOpt = trovaPianoxTarga(targa);

        //Se il piano è stato trovato, procedi
        if (pianoOpt.isPresent()) {
            Piano pianoTrovato = pianoOpt.get();

            //Trova lo scontrino ESATTO dentro il piano trovato
            //Questo è necessario per passarlo al metodo registraUscita del piano.
            Optional<Scontrino> scontrinoOpt = pianoTrovato.getScontrini().stream()
                    .filter(s -> s.getTarga_utente().equals(targa) && s.getOrario_Uscita() == null)
                    .findFirst();

            if (scontrinoOpt.isPresent()) {
                Scontrino scontrinoDaRegistrare = scontrinoOpt.get();
                pianoTrovato.registraUscita(scontrinoDaRegistrare, Data.Oggi(),Orario.adesso());
                return Optional.of(scontrinoDaRegistrare);
            }
        }
        return Optional.empty(); //se il piano o lo scontrino non sono stati trovati, restituisce un Optional vuoto
    }

    public static void salvaStato(GestioneParcheggio gestione, String nomeFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFile))) {
            oos.writeObject(gestione);
            System.out.println("Caricamento Completato!");
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento dei dati!");
        }
    }

    /**
     * Carica lo stato del parcheggio da un file.
     * @param nomeFile Il nome del file da cui caricare.
     * @return L'oggetto GestioneParcheggio caricato, o null se non trovato.
     */
    public static GestioneParcheggio caricaStato(String nomeFile) {
        File file = new File(nomeFile);
        if (!file.exists()) {
            System.out.println("Nessun dato precedente trovato. Inizio con un nuovo parcheggio.");
            return new GestioneParcheggio(); // Se il file non esiste, crea un nuovo parcheggio
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFile))) {
            GestioneParcheggio gestione = (GestioneParcheggio) ois.readObject();
            System.out.println("Dati del parcheggio caricati con successo da " + nomeFile);
            return gestione;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore durante il caricamento dei dati: " + e.getMessage());
            // In caso di errore, restituisce comunque un nuovo parcheggio vuoto
            return new GestioneParcheggio();
        }
    }

    public int contaPostiDisponibiliTotali() {
        return piani.stream()
                .mapToInt(Piano::getPostiDisponibili)
                .sum();
    }

    public int contaPostiOccupatiTotali() {
        return piani.stream()
                .mapToInt(Piano::getPostiOccupati)
                .sum();
    }

    public String visualizzaParcheggio() {
        for(Piano piano : piani) {
            System.out.println(piano.toString());
        }
        return null;
    }

    public String toString() {
        return visualizzaParcheggio();
    }
}








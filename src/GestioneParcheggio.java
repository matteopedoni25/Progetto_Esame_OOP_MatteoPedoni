import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class GestioneParcheggio {
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
                .filter(piano -> piano.getScontrino() // Per ogni piano, prendi la lista di scontrini
                        .stream() // Crea uno stream di scontrini
                        // anyMatch controlla se almeno un elemento dello stream matcha la condizione
                        .anyMatch(scontrino -> scontrino.getTarga_utente().equals(targa)))
                .findFirst(); // Prendi il primo piano che ha superato il filtro
    }
    public Optional<Scontrino> registraUscitaPerTarga(String targa) {
        // 1. Trova il piano dove si trova l'auto
        Optional<Piano> pianoOpt = trovaPianoxTarga(targa);

        // 2. Se il piano è stato trovato, procedi
        if (pianoOpt.isPresent()) {
            Piano pianoTrovato = pianoOpt.get();

            // 3. Ora trova lo scontrino ESATTO dentro il piano trovato
            //    Questo è necessario per passarlo al metodo registraUscita del piano.
            Optional<Scontrino> scontrinoOpt = pianoTrovato.getScontrino().stream()
                    .filter(s -> s.getTarga_utente().equals(targa))
                    .findFirst();

            if (scontrinoOpt.isPresent()) {
                Scontrino scontrinoDaRegistrare = scontrinoOpt.get();

                // 4. Registra l'uscita usando il metodo della classe Piano
                //    Assumiamo che Orario.adesso() ti dia l'orario corrente.
                Orario orarioDiUscita = Orario.adesso();
                 pianoTrovato.registraUscita(scontrinoDaRegistrare, orarioDiUscita);
            }
        }
        return Optional.empty();
    }
}







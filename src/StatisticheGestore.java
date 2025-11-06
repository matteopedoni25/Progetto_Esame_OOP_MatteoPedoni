import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticheGestore{
    private static final long serialVersionUID = 1L;
    private GestioneParcheggio gestioneParcheggio;

    public StatisticheGestore( GestioneParcheggio gestioneParcheggio) {
        this.gestioneParcheggio = gestioneParcheggio;
    }
    public double calcolaFatturatoTotale() {
        return gestioneParcheggio.getPiani().stream()
                .flatMap(piano -> piano.getScontrini().stream())
                .filter(Scontrino::Pay)
                .mapToDouble(Scontrino::getPrezzo)
                .sum();
    }

    public Map<Integer, Double> calcolaFatturatoPerPiano() {
        return gestioneParcheggio.getPiani().stream()
                .collect(Collectors.toMap(
                        Piano::getNumPiano,
                        piano -> piano.getScontrini().stream()
                                .filter(Scontrino::Pay)
                                .mapToDouble(Scontrino::getPrezzo)
                                .sum()
                ));
    }

    public double calcolaFatturatoPerGiorno(Data dataIN) {
        return gestioneParcheggio.getPiani().stream()
                .flatMap(piano -> piano.getScontrini().stream())
                .filter(Scontrino::Pay)
                .filter(scontrino -> scontrino.getDataOUT() != null && scontrino.getDataOUT().equals(dataIN))
                .mapToDouble(Scontrino::getPrezzo)
                .sum();
    }

    public double calcolaFatturatoPerMese(int anno, int mese) {
        return gestioneParcheggio.getPiani().stream()
                .flatMap(piano -> piano.getScontrini().stream()) // Unico stream di scontrini
                .filter(Scontrino::Pay) // Filtra solo quelli pagati (dataOUT != null)
                .filter(scontrino -> scontrino.getDataOUT() != null)
                .filter(scontrino -> {
                    // Ottiene la data di uscita dallo scontrino
                    Data dataUscita = scontrino.getDataOUT();
                    // Controlla che anno e mese corrispondano
                    return dataUscita.getAnno() == anno && dataUscita.getMese() == mese;
                })
                .mapToDouble(Scontrino::getPrezzo) // Estrae il prezzo
                .sum(); // Somma tutto
    }

    public double calcolaFatturatoPerAnno(int anno) {
        return gestioneParcheggio.getPiani().stream()
                .flatMap(piano -> piano.getScontrini().stream()) // Unico stream di scontrini
                .filter(Scontrino::Pay)// Filtra solo quelli pagati (dataOUT != null)
                .filter(scontrino -> scontrino.getDataOUT() != null)
                .filter(scontrino -> {
                    // Ottiene la data di uscita dallo scontrino
                    Data dataUscita = scontrino.getDataOUT();
                    // Controlla che anno e mese corrispondano
                    return dataUscita.getAnno() == anno;
                })
                .mapToDouble(Scontrino::getPrezzo) // Estrae il prezzo
                .sum(); // Somma tutto
    }




    /**
     * Calcola l'occupazione per ogni piano.
     * @return mappa con numero piano e percentuale di occupazione
    **/
    public Map<Integer, Double> calcolaOccupazionePerPiano() {
        return gestioneParcheggio.getPiani().stream()
                .collect(Collectors.toMap(
                        Piano::getNumPiano,
                        piano -> {
                            if (Piano.POSTI_X_PIANO == 0) {
                                return 0.0;
                            }
                            return (piano.getPostiOccupati() * 100.0) / Piano.POSTI_X_PIANO;
                        }
                ));
    }

    public long contaScontriniNonPagati() {
        return gestioneParcheggio.getPiani().stream()
                .flatMap(piano -> piano.getScontrini().stream())
                .filter(scontrino -> !scontrino.Pay())
                .count();
    }
    public int contaScontriniPerGiorno(Data dataIN) {
        return (int) gestioneParcheggio.getPiani().stream()
                .flatMap(piano -> piano.getScontrini().stream())
                .filter(Scontrino::Pay)
                .filter(scontrino -> Objects.equals(scontrino.getDataOUT(), dataIN))
                .count();
    }

}

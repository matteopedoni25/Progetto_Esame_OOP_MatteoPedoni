import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public int contaScontriniPerGiorno(Data dataIN) {
        return (int) gestioneParcheggio.getPiani().stream()
                .flatMap(piano -> piano.getScontrini().stream())
                .filter(Scontrino::Pay)
                .filter(scontrino -> Objects.equals(scontrino.getDataOUT(), dataIN))
                .count();
    }

}

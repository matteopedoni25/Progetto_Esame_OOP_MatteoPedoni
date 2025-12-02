import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.IOException;

public class StatisticheGestore{
    private static final long serialVersionUID = 1L;
    private Scanner scanner;
    private GestioneParcheggio gestioneParcheggio;

    public StatisticheGestore( GestioneParcheggio gestioneParcheggio) {
        this.gestioneParcheggio = gestioneParcheggio;
        this.scanner = new Scanner(System.in);
    }
    public double calcolaFatturatoTotale() {
        return gestioneParcheggio.getPiani().stream()
                .flatMap(piano -> piano.getScontrini().stream())
                .filter(scontrino -> scontrino.Pay() && scontrino.getDataOUT() != null && scontrino.getPrezzo() > 0.0)
                .mapToDouble(Scontrino::getPrezzo)
                .sum();
    }

    public Map<Integer, Double> calcolaFatturatoPerPiano() {
        return gestioneParcheggio.getPiani().stream()
                .collect(Collectors.toMap(
                        Piano::getNumPiano,
                        piano -> piano.getScontrini().stream()
                                .filter(scontrino -> scontrino.Pay() && scontrino.getDataOUT() != null && scontrino.getPrezzo() > 0.0)
                                .mapToDouble(Scontrino::getPrezzo)
                                .sum()
                ));
    }

    public double calcolaFatturatoPerGiorno(Data dataIN) {
        return gestioneParcheggio.getPiani().stream()
                .flatMap(piano -> piano.getScontrini().stream())
                .filter(scontrino -> scontrino.Pay() && scontrino.getDataOUT() != null && scontrino.getPrezzo() > 0.0)
                .filter(scontrino -> scontrino.getDataOUT() != null && scontrino.getDataOUT().equals(dataIN))
                .mapToDouble(Scontrino::getPrezzo)
                .sum();
    }

    public double calcolaFatturatoPerMese(int anno, int mese) {
        return gestioneParcheggio.getPiani().stream()
                .flatMap(piano -> piano.getScontrini().stream()) // Unico stream di scontrini
                .filter(scontrino -> scontrino.Pay() && scontrino.getDataOUT() != null && scontrino.getPrezzo() > 0.0) // Filtra solo quelli pagati (dataOUT != null)
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
                .filter(scontrino -> scontrino.Pay() && scontrino.getDataOUT() != null && scontrino.getPrezzo() > 0.0)// Filtra solo quelli pagati (dataOUT != null)
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

    public double media(double a , double b){
        double media = (a/b);
        if(b > 0.0){
        return media;}
        else return 0.0;
    }

   public String generaReport(Data dataIN) {
        StringBuilder sb = new StringBuilder();

        // Titolo
        sb.append("=== REPORT PARCHEGGIO ===\n\n");
        sb.append("Statische del :").append(dataIN).append("\n");//data In sarà inserita con il metodo RichiediData()
        //Statistiche Generali
        sb.append("== STATISTICHE PARCHEGGIO ===\n");
        sb.append("Il parcheggio dispone di: ").append(gestioneParcheggio.contaPostiDisponibiliTotali()).append(" posti totali").append("\n");
        sb.append("Al momento le percentuali di occupazione per piano sono: ").append(calcolaOccupazionePerPiano()).append("\n");
        sb.append("In data odierna ").append(dataIN).append(" il fatturato è pari a: ").append(calcolaFatturatoPerGiorno(dataIN)).append("€").append("\n");
        sb.append("Sono stati stampati: ").append(contaScontriniPerGiorno(dataIN)).append(" scontrini").append("\n");
        sb.append("Per una media di incasso medio di: ").append(media(calcolaFatturatoPerGiorno(dataIN), contaScontriniPerGiorno(dataIN))).append("€").append("\n");
        return sb.toString();
    }

    public void salvaSuFile(String nomeFile) {
        Data data = Data.richiediData(this.scanner);
        String stats = generaReport(data);
        try (FileWriter writer = new FileWriter(nomeFile)) {
            writer.write(stats);
            System.out.println("File salvato correttamente!");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }



}

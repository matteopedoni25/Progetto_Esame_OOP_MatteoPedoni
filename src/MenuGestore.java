import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;


public class MenuGestore {
    private GestioneParcheggio gestioneParcheggio;
    private Piano piano;
    private Scanner scanner;
    private StatisticheGestore statisticheGestore;

    public MenuGestore(GestioneParcheggio gestioneParcheggio, Scanner scanner) {
        this.gestioneParcheggio = gestioneParcheggio;
        this.scanner = scanner;
        this.statisticheGestore = new StatisticheGestore(gestioneParcheggio);
    }
    public void MostraMenuGestore(){
        boolean continua = true;
        while(continua){
            System.out.println(" === MENU GESTORE === ");
            System.out.println("1. Statistiche fatturato ");
            System.out.println("2. Statistiche Occupazione");
            System.out.println("3. Esporta Statistiche");
            System.out.println("4. Torna al Menu Principale");
            System.out.println("Inserisci Selezione: ");
            int op= 0;
            try {
                op = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("ERRORE INSERIMENTO!");
                scanner.nextLine();
                continue;
            }
            switch (op) {
                case 1:
                    MostraMenuFatturato();
                    System.out.println("\n");
                    break;
                case 2:
                    MostraMenuOccupazione();
                    System.out.println("\n");
                    break;
                case 3:

                    break;
                case 4:
                    continua = false;
                    break;
                default:
                    System.out.println("Inserimento non valido!");
            }
        }
    }
    public void MostraMenuFatturato() {
        boolean continua = true;
        while (continua) {
            System.out.println(" === MENU FATTURATO === ");
            System.out.println("1. Fatturato TOT");
            System.out.println("2. Fatturato per Piano");
            System.out.println("3. Visualizza Fatturato per un giorno SPECIFICO");
            System.out.println("4. Visualizza Fatturato per Mese");
            System.out.println("5. Visualizza Fatturato per Anno");
            System.out.println("6. Torna la Menu Gestore ");
            System.out.println("Inserisci Selezione: ");
            int op = 0;
            try {
                op = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("ERRORE INSERIMENTO!");
                scanner.nextLine();
                continue;
            }
            switch (op) {
                case 1:
                    double fatturatoTOT= statisticheGestore.calcolaFatturatoTotale();
                    System.out.println(fatturatoTOT);
                    System.out.println("\n");
                    break;
                case 2:
                    Map<Integer, Double> fatturatoPiano = statisticheGestore.calcolaFatturatoPerPiano();
                    System.out.println(fatturatoPiano);
                    System.out.println("\n");
                    break;
                case 3:
                    Data dataRichiesta = richiediData(this.scanner);
                   double fatturatoGiorno = statisticheGestore.calcolaFatturatoPerGiorno(dataRichiesta);
                   int contaScontrini = statisticheGestore.contaScontriniPerGiorno(dataRichiesta);
                    double mediaPrezzoScontrino = 0.0;
                    if (contaScontrini > 0) {
                        mediaPrezzoScontrino = fatturatoGiorno / contaScontrini;
                    }
                   System.out.println("Fatturato: "+fatturatoGiorno+"\nScontrini completati: "+contaScontrini+"\nMedia Prezzo Scontrino: "+mediaPrezzoScontrino);
                    System.out.println("\n");
                    break;
                case 4:
                    int mese = 0;
                    int anno= 0;
                    try {
                        System.out.println("Inserisci la data:");

                        System.out.print("Anno (YYYY): ");
                         anno = scanner.nextInt();

                        System.out.print("Mese (1-12): ");
                         mese = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("ERRORE: Devi inserire solo numeri. Riprova.");
                        scanner.nextLine();
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERRORE: " + e.getMessage() + ". Riprova.");
                    }
                    double fatMese = statisticheGestore.calcolaFatturatoPerMese(anno, mese);
                    System.out.println("Fatturato mensile: "+fatMese);
                    System.out.println("\n");
                    break;
                case 5:
                    int Year = 0;
                    try {
                        System.out.println("Inserisci la data:");

                        System.out.print("Anno (YYYY): ");
                        Year = scanner.nextInt();

                    } catch (InputMismatchException e) {
                        System.out.println("ERRORE: Devi inserire solo numeri. Riprova.");
                        scanner.nextLine();
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERRORE: " + e.getMessage() + ". Riprova.");
                    }
                    double fatAnno = statisticheGestore.calcolaFatturatoPerAnno(Year);
                    System.out.println("Fatturato annuale: "+fatAnno);
                    System.out.println("\n");
                    break;
                case 6:
                    continua = false;
                    break;
                default:
                    System.out.println("Inserimento scelta invalida");
            }
        }
    }

    public void MostraMenuOccupazione(){
        boolean continua = true;
        while (continua) {
            System.out.println(" === MENU OCCUPAZIONE === ");
            System.out.println("1. Visualizza statistiche Occupazione");
            System.out.println("2.  ");
            System.out.println("3. Visualizza scontrini in sospeso: ");
            System.out.println("4. Torna al Menu Gestore");
            System.out.println("Inserisci Selezione: ");
            int op = 0;
            try {
                op = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("ERRORE INSERIMENTO!");
                scanner.nextLine();
                continue;
            }
            switch (op) {
                case 1:
                   visualizzaOccupazione();
                    System.out.println("\n");
                    break;
                case 2:
                    break;
                case 3:
                    long scontrini = statisticheGestore.contaScontriniNonPagati();
                    System.out.println("Scontrini da pagare: "+scontrini);
                    System.out.println("\n");
                    break;
                case 4:
                    continua = false;
                    break;
                default:
                    System.out.println("Inserimento scelta invalida");

            }
        }
    }

    public Data richiediData(Scanner scanner) {
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

                // 1. Tenta di creare l'oggetto Data usando il tuo costruttore
                // 2. Il costruttore chiamerà DataValida()
                // 3. Se la data non è valida, lancerà IllegalArgumentException
                Data dataInserita = new Data(anno, mese, giorno);

                // 4. Se arriva qui, la data è valida. Esce dal ciclo e restituisce.
                return dataInserita;

            } catch (InputMismatchException e) {
                // Errore: l'utente ha inserito testo invece di un numero
                System.out.println("ERRORE: Devi inserire solo numeri. Riprova.");
                scanner.nextLine(); // Pulisce lo scanner dall'input errato
            } catch (IllegalArgumentException e) {
                // Errore: i numeri formano una data non valida (es. 30/02/2024)
                // Questo messaggio d'errore (e.getMessage()) arriva da "La data non è valida"
                // nel tuo costruttore Data
                System.out.println("ERRORE: " + e.getMessage() + ". Riprova.");
            }
        }
    }

    private void visualizzaOccupazione() {
        System.out.println("\n--- OCCUPAZIONE PARCHEGGIO ---");
        Map<Integer, Double> occupazionePerPiano = statisticheGestore.calcolaOccupazionePerPiano();
        System.out.println("Percentuale occupazione per piano: "+occupazionePerPiano);
        System.out.println("Posti occupati: " + gestioneParcheggio.contaPostiOccupatiTotali());
        System.out.println("Posti disponibili: " + gestioneParcheggio.contaPostiDisponibiliTotali());
    }
}
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;


public class MenuGestore {
    private GestioneParcheggio gestioneParcheggio;
    private Scanner scanner;
    private StatisticheGestore statisticheGestore;

    public MenuGestore(GestioneParcheggio gestioneParcheggio, Scanner scanner) {
        this.gestioneParcheggio = gestioneParcheggio;
        this.scanner = scanner;
        this.statisticheGestore = new StatisticheGestore(gestioneParcheggio);

    }

    public void MostraMenuGetsore() {
        boolean continua = true;
        while (continua) {
            System.out.println(" === MENU GESTORE === ");
            System.out.println("1. FATTURATO TOTALE");
            System.out.println("2. Fatturato per Piano");
            System.out.println("3. Visualizza Fatturato per un giorno SPECIFICO ");
            System.out.println("4. Torna la Menu Principale ");
            System.out.println("Inserisci Selezione: ");
            int op = 0;
            try {
                op = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("ERRORE INSERIMENTO!!\nINSERISCI UN NUMERO DA 1 A 5");
                scanner.nextLine();
                continue;
            }
            switch (op) {
                case 1:
                    double fatturatoTOT= statisticheGestore.calcolaFatturatoTotale();
                    System.out.println(fatturatoTOT);
                    break;
                case 2:
                    Map<Integer, Double> fatturatoPiano = statisticheGestore.calcolaFatturatoPerPiano();
                    System.out.println(fatturatoPiano);
                    break;
                case 3:
                    Data dataRichiesta = richiediData(this.scanner);
                   double fatturatoGiorno = statisticheGestore.calcolaFatturatoPerGiorno(dataRichiesta);
                   System.out.println(fatturatoGiorno);
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
}
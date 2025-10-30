import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipale {
    private GestioneParcheggio gestioneParcheggio;
    private Scanner scanner;
    private MenuUtente menuUtente;
    private static final String NOME_FILE_SALVATAGGIO = "parcheggio.ser"; // Nome del file di salvataggio

    public MenuPrincipale(){
        this.gestioneParcheggio = GestioneParcheggio.caricaStato(NOME_FILE_SALVATAGGIO);
        this.scanner = new Scanner(System.in);
        this.menuUtente = new MenuUtente(gestioneParcheggio, scanner);
    }

    public void avviaMenu(){
        System.out.println("===========================================");
        System.out.println("   SISTEMA GESTIONE PARCHEGGIO MULTIPIANO");
        System.out.println("===========================================");
        while(true){
            System.out.println("\n=== MENU PRINCIPALE ===");
            System.out.println("1. Modalità Utente");
            System.out.println("2. Modalità Gestore");
            System.out.println("3. Salva ed Esci");
            System.out.println("Inserisci Selezione: ");
            int op = 0;
            try{
                op = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("ERRORE INSERIMENTO!!\nINSERISCI UN NUMERO DA 1 A 2");
                scanner.nextLine();
                continue;
            }
            switch(op){
                case 1:
                    menuUtente.MostraMenu();
                    break;
                case 2:
                    // Qui andrebbe il menu gestore
                    System.out.println("Modalità gestore non ancora implementata.");
                    break;
                case 3:
                    // Salva lo stato attuale prima di uscire
                    gestioneParcheggio.salvaStato(gestioneParcheggio, NOME_FILE_SALVATAGGIO);
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Inserimento scelta invalida");
            }
        }
    }
}

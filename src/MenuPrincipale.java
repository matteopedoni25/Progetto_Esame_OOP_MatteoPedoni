import java.util.Scanner;

public class MenuPrincipale {
    private GestioneParcheggio gestioneParcheggio;
    private Scanner scanner;
    private MenuUtente menuUtente;


    public MenuPrincipale(){
        this.gestioneParcheggio = new GestioneParcheggio();
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
            System.out.println("Inserisci Selezione: ");
            int in =  scanner.nextInt();
            scanner.nextLine();
            switch(in){
                case 1:
                    menuUtente.MostraMenu();
                    break;
                default:
                    System.out.println("Inserimento scelta invalida");
            }
        }
    }
}

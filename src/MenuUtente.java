import  java.util.Scanner;


public class MenuUtente {
    private GestioneParcheggio gestioneParcheggio;
    private Scanner scanner;

    public MenuUtente(GestioneParcheggio gestioneParcheggio,  Scanner scanner) {
        this.gestioneParcheggio = gestioneParcheggio;
        this.scanner = scanner;
    }

    public void MostraMenu(){
        while(true){
            System.out.println(" === MENU UTENTE === ");
            while(true){
                System.out.println("1. Registra Ingresso");
                System.out.println("2. Registra Uscita");
                System.out.println("3. Verifica Disponibilità");
                System.out.println("Inserisci Selezione: ");
                int op = scanner.nextInt();
                scanner.nextLine();
                switch(op){
                    case 1:
                        System.out.println("Inserisci targa: ");
                        String targaIN = scanner.next();
                        gestioneParcheggio.RegistraIngresso(targaIN);
                        System.out.println(gestioneParcheggio.getPiani());
                        break;
                    case 2:
                        System.out.println("Inserisci targa: ");
                        String targaOUT = scanner.next();
                        gestioneParcheggio.registraUscitaPerTarga(targaOUT);
                        System.out.println(gestioneParcheggio.getPiani());
                        break;
                    case 3:
                    default:
                        System.out.println("Inserimento scelta invalida");
                }
            }
        }
    }
}

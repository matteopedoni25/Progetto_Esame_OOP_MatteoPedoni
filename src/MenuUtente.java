import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;


public class MenuUtente {
    private GestioneParcheggio gestioneParcheggio;
    private Scanner scanner;

    public MenuUtente(GestioneParcheggio gestioneParcheggio,  Scanner scanner) {
        this.gestioneParcheggio = gestioneParcheggio;
        this.scanner = scanner;
    }

    public void MostraMenuUtente(){
        boolean continua = true;
        while(continua){
                System.out.println(" === MENU UTENTE === ");
                System.out.println("1. Registra Ingresso");
                System.out.println("2. Registra Uscita");
                System.out.println("3. Verifica stato parcheggio");
                System.out.println("4. Trova il tuo veicolo: ");
                System.out.println("5. Torna al Menu Principale: ");
                System.out.println("Inserisci Selezione: ");
                int op = 0;
                try{
                 op = scanner.nextInt();
                scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("ERRORE INSERIMENTO!!\nINSERISCI UN NUMERO DA 1 A 4");
                    scanner.nextLine();
                    continue;
                }
                switch(op){
                    case 1:
                        registraIngresso();
                        break;
                    case 2:
                        registraUscita();
                        break;
                    case 3:
                        gestioneParcheggio.toString();
                            break;
                    case 4:
                        System.out.println("Inserisci targa: ");
                        String targaV = scanner.next();
                        Optional<Piano> pianoOpt =  gestioneParcheggio.trovaPianoxTarga(targaV);
                        Piano piano = pianoOpt.get();
                        System.out.println("Il tuo veicolo si trova al piano: "+piano.getNumPiano());
                        break;
                    case 5:
                        continua = false;
                        break;
                    default:
                        System.out.println("Inserimento scelta invalida");
                }
            }
        }

    public void registraIngresso(){
        System.out.println("Inserisci targa: ");
        String targaIN = scanner.next();
        Optional<Piano> check =  gestioneParcheggio.trovaPianoxTarga(targaIN); //Controllo se la targa è gia presente nel parcheggio
        if(!check.isPresent()){
            Optional<Scontrino> scontrinoOpt = gestioneParcheggio.RegistraIngresso(targaIN);
            Scontrino scontrino = scontrinoOpt.orElse(null);
            Optional<Piano> pianoOpt =   gestioneParcheggio.trovaPianoxTarga(targaIN);
            Piano piano = pianoOpt.get();
            System.out.println("Recarsi al piano: "+piano.getNumPiano());
            System.out.println("Conserva lo scontrino fino all'uscita: ");
            System.out.println(scontrino.stampaIngresso());
        }
        else{
            System.out.println("ERRORE: La targa inserita è gia presente nel parcheggio");
        }
    }

    public void registraUscita(){
        System.out.println("Inserisci targa: ");
        String targaOUT = scanner.next();
        Optional<Scontrino> scontrinoOpt = gestioneParcheggio.registraUscitaPerTarga(targaOUT);
        if (scontrinoOpt.isPresent()) {
            System.out.println("Uscita registrata con successo!");
            System.out.println("Scontrino finale: ");
            System.out.println(scontrinoOpt.get());
        }
        else {
            System.out.println("ERRORE: Impossibile registrare l'uscita. Veicolo non trovato o già uscito. ");
        }

    }
}

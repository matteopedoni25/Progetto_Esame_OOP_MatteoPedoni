
public class Main {

            static void main() {
                MenuPrincipale menuPrincipale = new MenuPrincipale();
                GestioneParcheggio.caricaStato("parcheggio.ser");
                menuPrincipale.avviaMenu();
            }
}


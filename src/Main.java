
public class Main {

            public static void main(String[] args) {
                MenuPrincipale menuPrincipale = new MenuPrincipale();
                GestioneParcheggio.caricaStato("parcheggio.ser");
                menuPrincipale.avviaMenu();
            }
}


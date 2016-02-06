package race;


/**
 *
 * @author TERMIN
 */
public class Race {

    public static void main(String[] args) {
        Core core = new Core();
        try {
            core.main();
        } catch (InterruptedException ex) {
            System.err.println("Что то сломалось...");
            ex.getMessage();
        }
                
    }
    
}

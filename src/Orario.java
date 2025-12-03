import java.io.Serializable;
import java.time.LocalTime;

public class Orario implements Serializable {
    private static final long serialVersionUID = 1L;
    private int ora;
    private int minuti;


    public Orario(int ora, int minuti) {
        this.ora = ora;
        this.minuti = minuti;
    }

    public static Orario adesso() {
        LocalTime adesso = LocalTime.now();
        return new Orario(adesso.getHour(), adesso.getMinute());
    }
    public LocalTime toLocalTime() {
        return LocalTime.of(ora, minuti);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", ora, minuti);
    }
}

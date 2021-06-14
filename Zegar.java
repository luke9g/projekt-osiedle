import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Zegar extends Thread {
    public static int dzien = 0;
    private Osiedle osiedle;
    private Osoba uzytkownik;

    public Zegar(Osiedle osiedle, Osoba uzytkownik) {
        this.osiedle = osiedle;
        this.uzytkownik = uzytkownik;
    }

    public void setUzytkownik(Osoba uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public static int getDzien() {
        return dzien;
    }

    public static String getData() {
        return obliczDate(dzien);
    }

    public static String obliczDate(int liczbaDni) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, liczbaDni);
        return sdf.format(c.getTime());
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return;
            }
            dzien++;
            osiedle.sprawdzStanNajmow(uzytkownik);
        }
    }
}

import java.io.File;
import java.util.ArrayList;

public class Osoba {
    private static int generator = 0;

    private String imie;
    private String nazwisko;
    private final String pesel;
    private String adres;
    private final String dataUrodzenia;
    private final int numer;
    private final String id;
    private final ArrayList<Pomieszczenie> wynajetePomieszczenia;
    private final ArrayList<Pomieszczenie> zamieszkanePomieszczenia;  // jako lokator
    private final ArrayList<Przedmiot> posiadanePrzedmioty;
    private final ArrayList<File> zadluzenia;

    public Osoba(String imie, String nazwisko, String pesel, String adres, String dataUrodzenia) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.adres = adres;
        this.dataUrodzenia = dataUrodzenia;
        numer = ++generator;
        id = "o" + numer;
        wynajetePomieszczenia = new ArrayList<>();
        zamieszkanePomieszczenia = new ArrayList<>();
        posiadanePrzedmioty = new ArrayList<>();
        zadluzenia = new ArrayList<>();
    }

    public void dodajNajem(Pomieszczenie pom) {
        wynajetePomieszczenia.add(pom);
    }

    public void usunNajem(Pomieszczenie pom) {
        wynajetePomieszczenia.remove(pom);
    }

    public void dodajZamieszkanie(Mieszkanie m) {
        zamieszkanePomieszczenia.add(m);
    }

    public void usunZamieszkanie(Mieszkanie m) {
        zamieszkanePomieszczenia.remove(m);
    }

    public void dodajZadluzenie(Pomieszczenie pom) {
        zadluzenia.add(new File(pom.toString()));
    }

    public void usunZadluzenie(Pomieszczenie pom) {
        zadluzenia.remove(new File(pom.toString()));
    }

    public void zameldujLokatora(Osoba o, String idMieszkania) {
        if (o == null) {
            System.out.println("Nie ma takiej osoby.");
            return;
        }
        if (o.equals(this)) {
            System.out.println("Nie możesz dodać samego siebie jako lokatora!");
            return;
        }
        for (Pomieszczenie pom : wynajetePomieszczenia) {
            if (pom.getTyp() == TypPomieszczenia.MIESZKANIE && pom.getId().equals(idMieszkania)) {
                Mieszkanie m = (Mieszkanie) pom;
                if (m.czyLokator(o)) {
                    System.out.println("Ta osoba już jest lokatorem mieszkania " + idMieszkania + "!");
                    return;
                }
                m.dodajLokatora(o);
                o.dodajZamieszkanie(m);
                System.out.println("Zameldowałeś lokatora " + o + " do mieszkania " + idMieszkania + ".");
                return;
            }
        }
        System.out.println("Nie wynajmujesz mieszkania o takim id!");
    }

    public void wymeldujLokatora(Osoba o, String idMieszkania) {
        if (o == null) {
            System.out.println("Nie ma takiej osoby.");
            return;
        }
        for (Pomieszczenie pom : wynajetePomieszczenia) {
            if (pom.getTyp() == TypPomieszczenia.MIESZKANIE && pom.getId().equals(idMieszkania)) {
                Mieszkanie m = (Mieszkanie) pom;
                if (!m.czyLokator(o)) {
                    System.out.println("Ta osoba nie jest lokatorem mieszkania " + idMieszkania + "!");
                    return;
                }
                m.usunLokatora(o);
                o.usunZamieszkanie(m);
                System.out.println("Wymeldowałeś lokatora " + o + " z mieszkania " + idMieszkania + ".");
                return;
            }
        }
        System.out.println("Nie wynajmujesz mieszkania o takim id!");
    }

    public void wlozPrzedmiot(Przedmiot prz, String idMiejscaParkingowego) {
        if (prz == null) {
            System.out.println("Nie ma takiego przedmiotu.");
            return;
        }
        MiejsceParkingowe miejscePrzedmiotu = prz.getMiejsce();
        if (miejscePrzedmiotu != null) {
            System.out.println("Ten przedmiot już znajduje się na " + miejscePrzedmiotu + "!");
            return;
        }
        for (Pomieszczenie pom : wynajetePomieszczenia) {
            if (pom.getTyp() == TypPomieszczenia.MIEJSCE_PARKINGOWE && pom.getId().equals(idMiejscaParkingowego)) {
                MiejsceParkingowe p = (MiejsceParkingowe) pom;
                try {
                    p.dodajPrzedmiot(prz);
                } catch (TooManyThingsException e) {
                    System.err.println(e.getMessage());
                    return;
                }
                prz.setMiejsce(p);
                posiadanePrzedmioty.add(prz);
                System.out.println("Włożyłeś " + prz + " na " + p + ".");
                return;
            }
        }
        System.out.println("Nie wynajmujesz miejsca parkingowego o takim id!");
    }

    public void wyjmijPrzedmiot(Przedmiot prz, String idMiejscaParkingowego) {
        if (prz == null) {
            System.out.println("Nie ma takiego przedmiotu.");
            return;
        }
        MiejsceParkingowe miejscePrzedmiotu = prz.getMiejsce();
        if (miejscePrzedmiotu == null) {
            System.out.println("Ten przedmiot nie znajduje się na żadnym miejscu parkingowym!");
            return;
        }
        for (Pomieszczenie pom : wynajetePomieszczenia) {
            if (pom.getTyp() == TypPomieszczenia.MIEJSCE_PARKINGOWE && pom.getId().equals(idMiejscaParkingowego)) {
                MiejsceParkingowe p = (MiejsceParkingowe) pom;
                p.usunPrzedmiot(prz);
                prz.setMiejsce(null);
                posiadanePrzedmioty.remove(prz);
                System.out.println("Wyjąłeś " + prz + " z " + p + ".");
                return;
            }
        }
        System.out.println("Nie wynajmujesz miejsca parkingowego o takim id!");
    }

    public void usunPrzedmiot(Przedmiot prz) {
        posiadanePrzedmioty.remove(prz);
    }

    public void przedluzNajem(String idPomieszczenia) {
        for (Pomieszczenie pom : wynajetePomieszczenia) {
            if (pom.getId().equals(idPomieszczenia)) {
                if (Zegar.getDzien() <= pom.getDzienZakonczeniaNajmu()) {
                    System.out.println("Nie możesz jeszcze przedłużyć najmu " + pom + ". Spróbuj później.");
                    return;
                }
                pom.przedluzNajem(Pomieszczenie.OKRES_NAJMU);
                usunZadluzenie(pom);
                System.out.println("Pomyślnie przedłużyłeś najem " + pom + " o kolejne " + Pomieszczenie.OKRES_NAJMU + " dni.");
                return;
            }
        }
        System.out.println("Nie wynajmujesz pomieszczenia o takim id!");
    }

    public void anulujNajem(String idPomieszczenia) {
        for (Pomieszczenie pom : new ArrayList<>(wynajetePomieszczenia)) {
            if (pom.getId().equals(idPomieszczenia)) {
                if (Zegar.getDzien() <= pom.getDzienZakonczeniaNajmu()) {
                    System.out.println("Nie możesz jeszcze anulować najmu " + pom + ". Spróbuj później.");
                    return;
                }
                pom.zakonczNajem();
                usunZadluzenie(pom);
                wynajetePomieszczenia.remove(pom);
                System.out.println("Pomyślnie anulowałeś najem " + pom + ".");
                return;
            }
        }
        System.out.println("Nie wynajmujesz pomieszczenia o takim id!");
    }

    public void przedluzWszystkie() {
        if (wynajetePomieszczenia.size() == 0) {
            System.out.println("Nie wynajmujesz żadnych pomieszczeń!");
        }
        for (Pomieszczenie pom : wynajetePomieszczenia) {
            if (Zegar.getDzien() <= pom.getDzienZakonczeniaNajmu()) {
                System.out.println("Nie możesz jeszcze przedłużyć najmu " + pom + ". Spróbuj później.");
                continue;
            }
            pom.przedluzNajem(Pomieszczenie.OKRES_NAJMU);
            usunZadluzenie(pom);
            System.out.println("Pomyślnie przedłużyłeś najem " + pom + " o kolejne " + Pomieszczenie.OKRES_NAJMU + " dni.");
        }
    }

    public void anulujWszystkie() {
        if (wynajetePomieszczenia.size() == 0) {
            System.out.println("Nie wynajmujesz żadnych pomieszczeń!");
        }
        for (Pomieszczenie pom : new ArrayList<>(wynajetePomieszczenia)) {
            if (Zegar.getDzien() <= pom.getDzienZakonczeniaNajmu()) {
                System.out.println("Nie możesz jeszcze anulować najmu " + pom + ". Spróbuj później.");
                continue;
            }
            pom.zakonczNajem();
            usunZadluzenie(pom);
            wynajetePomieszczenia.remove(pom);
            System.out.println("Pomyślnie anulowałeś najem " + pom + ".");
        }
    }

    public boolean mozeWynajacMiejsceParkingowe() {
        for (Pomieszczenie pom: wynajetePomieszczenia) {
            if (pom.getTyp() == TypPomieszczenia.MIESZKANIE) {
                return true;
            }
        }
        return false;
    }

    public int getLiczbaNajmow() {
        return wynajetePomieszczenia.size();
    }

    public int getNumer() {
        return numer;
    }

    public String getId() {
        return id;
    }

    public ArrayList<File> getZadluzenia() {
        return zadluzenia;
    }

    public int getLiczbaZadluzen() {
        return zadluzenia.size();
    }

    public void wyswietlNajmy() {
        if (wynajetePomieszczenia.size() == 0) {
            System.out.println("Nie wynajmujesz żadnych pomieszczeń.");
        }
        for (Pomieszczenie pom : wynajetePomieszczenia) {
            System.out.println(pom.najmInfo());
        }
    }

    public void wyswietlPelneInfo() {
        System.out.println(pelneInfo());
    }

    public void wyswietlKrotkieInfo() {
        System.out.println(krotkieInfo());
    }

    public String pelneInfo() {
        return this.toString() +
                "\n\tpesel: " + pesel +
                "\n\tadres: " + adres +
                "\n\tdata urodzenia: " + dataUrodzenia +
                "\n\twynajęte pomieszczenia: " + (wynajetePomieszczenia.size() == 0 ? "BRAK" : wynajetePomieszczenia) +
                "\n\tzadłużone najmy: " + (zadluzenia.size() == 0 ? "BRAK" : zadluzenia) +
                "\n\tzamieszkane pomieszczenia: " + (zamieszkanePomieszczenia.size() == 0 ? "BRAK" : zamieszkanePomieszczenia) +
                "\n\tposiadane przedmioty: " + (posiadanePrzedmioty.size() != 0 ? posiadanePrzedmioty : "BRAK");
    }

    public String krotkieInfo() {
        return this.toString() + " - wynajęte pomieszczenia: " + (wynajetePomieszczenia.size() == 0 ? "BRAK" : wynajetePomieszczenia);
    }

    @Override
    public String toString() {
        return imie + " " + nazwisko + " " + id;
    }
}

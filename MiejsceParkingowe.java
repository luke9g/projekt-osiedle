import java.util.ArrayList;
import java.util.Comparator;

public class MiejsceParkingowe extends Pomieszczenie {
    private static int generator = 0;

    private final ArrayList<Przedmiot> przedmioty;
    private int zajetaPowierzchnia;

    public MiejsceParkingowe(int objetosc, int nrBloku) {
        super(objetosc, 5, "p" + ++generator, TypPomieszczenia.MIEJSCE_PARKINGOWE, nrBloku);
        przedmioty = new ArrayList<>();
        zajetaPowierzchnia = 0;
    }

    public MiejsceParkingowe(int dlugosc, int szerokosc, int wysokosc, int nrBloku) {
        super(dlugosc, szerokosc, wysokosc, "p" + ++generator, TypPomieszczenia.MIEJSCE_PARKINGOWE, nrBloku);
        przedmioty = new ArrayList<>();
        zajetaPowierzchnia = 0;
    }

    public void dodajPrzedmiot(Przedmiot prz) throws TooManyThingsException {
        if (super.getPowierzchnia() < zajetaPowierzchnia + prz.getPowierzchnia()) {
            throw new TooManyThingsException();
        }
        przedmioty.add(prz);
        zajetaPowierzchnia += prz.getPowierzchnia();
        uporzadkujPrzedmioty();
    }

    public void usunPrzedmiot(Przedmiot prz) {
        przedmioty.remove(prz);
        zajetaPowierzchnia -= prz.getPowierzchnia();
        uporzadkujPrzedmioty();
    }

    private void uporzadkujPrzedmioty() {
        przedmioty.sort(Comparator.comparingInt(Przedmiot::getPowierzchnia).reversed().thenComparing(Przedmiot::getNazwa));
    }

    public ArrayList<Przedmiot> getPrzedmioty() {
        return przedmioty;
    }

    public Pojazd sprzedajPojazd() {
        Pojazd pojazd = null;
        for (Przedmiot prz : przedmioty) {
            if (prz.getTyp() != TypPrzedmiotu.PRZEDMIOT) {
                pojazd = (Pojazd) prz;
                break;
            }
        }
        return pojazd;
    }

    @Override
    protected void wyczyscPomieszczenie() {
        for (Przedmiot prz : przedmioty) {
            prz.setMiejsce(null);
            getNajemca().usunPrzedmiot(prz);
        }
        przedmioty.clear();
        zajetaPowierzchnia = 0;
    }

    @Override
    public String pelneInfo() {
        return super.pelneInfo() +
                "\n\tprzedmioty: " + (przedmioty.size() != 0 ? przedmioty : "BRAK") +
                "\n\tzajęta powierzchnia: " + zajetaPowierzchnia + " m2";
    }

    @Override
    public String krotkieInfo() {
        return super.krotkieInfo() + ", przedmioty: " + (przedmioty.size() != 0 ? przedmioty : "BRAK");
    }
}

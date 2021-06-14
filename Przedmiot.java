public class Przedmiot {
    private static int generator = 0;

    private String nazwa;
    private final int powierzchnia;
    private MiejsceParkingowe miejsce;
    private final int numer;
    private final String id;
    private TypPrzedmiotu typ;

    public Przedmiot(int powierzchnia, String nazwa) {
        this.powierzchnia = powierzchnia;
        this.nazwa = nazwa;
        typ = TypPrzedmiotu.PRZEDMIOT;
        miejsce = null;
        numer = ++generator;
        id = "prz" + numer;
    }

    protected Przedmiot(int powierzchnia, String nazwa, TypPrzedmiotu typ) {
        this(powierzchnia, nazwa);
        this.typ = typ;
    }

    public Przedmiot(int dlugosc, int szerokosc, String nazwa) {
        this(dlugosc * szerokosc, nazwa);
    }

    protected Przedmiot(int dlugosc, int szerokosc, String nazwa, TypPrzedmiotu typ) {
        this(dlugosc * szerokosc, nazwa);
        this.typ = typ;
    }

    public void setMiejsce(MiejsceParkingowe p) {
        this.miejsce = p;
    }

    public int getPowierzchnia() {
        return powierzchnia;
    }

    public String getNazwa() {
        return nazwa;
    }

    public TypPrzedmiotu getTyp() {
        return typ;
    }

    public MiejsceParkingowe getMiejsce() {
        return miejsce;
    }

    public int getNumer() {
        return numer;
    }

    public String getId() {
        return id;
    }

    public String pelneInfo() {
        return this.toString() +
                "\n\tpole powierzchni: " + powierzchnia + " m2" +
                "\n\tmiejsce parkingowe: " + (miejsce != null ? miejsce : "BRAK");
    }

    public String krotkieInfo() {
        return this.toString() + " - miejsce parkingowe: " + (miejsce != null ? miejsce : "BRAK");
    }

    @Override
    public String toString() {
        return typ + " " + nazwa + " " + id;
    }
}

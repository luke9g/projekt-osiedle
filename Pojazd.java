abstract class Pojazd extends Przedmiot {
    private final boolean isElektryczny;
    private Silnik<Integer> silnik;

    public Pojazd(int powierzchnia, String marka, boolean isElektryczny, int mocSilnikaKM, TypPrzedmiotu typ) {
        super(powierzchnia, marka, typ);
        this.isElektryczny = isElektryczny;
        silnik = new Silnik<>(mocSilnikaKM);
    }

    public Pojazd(int dlugosc, int szerokosc, String marka, boolean isElektryczny, int mocSilnikaKM, TypPrzedmiotu typ) {
        super(dlugosc, szerokosc, marka, typ);
        this.isElektryczny = isElektryczny;
        silnik = new Silnik<>(mocSilnikaKM);
    }

    @Override
    public String pelneInfo() {
        return super.pelneInfo() +
                "\n\telektryczny: " + (isElektryczny? "TAK" : "NIE") +
                "\n\tmoc silnika [konie mechaniczne]: " + silnik.getMocKM() + " KM" +
                "\n\tmoc silnika [kilowaty]: " + String.format("%.2f", silnik.obliczMocKW()) + " KW";
    }
}

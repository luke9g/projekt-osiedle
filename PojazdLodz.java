public class PojazdLodz extends Pojazd {
    private String nazwaLodzi;

    public PojazdLodz(int powierzchnia, String marka, boolean isElektryczny, int mocSilnikaKM, String nazwaLodzi) {
        super(powierzchnia, marka, isElektryczny, mocSilnikaKM, TypPrzedmiotu.LODZ);
        this.nazwaLodzi = nazwaLodzi;
    }

    public PojazdLodz(int dlugosc, int szerokosc, String marka, boolean isElektryczny, int mocSilnikaKM, String nazwaLodzi) {
        super(dlugosc, szerokosc, marka, isElektryczny, mocSilnikaKM, TypPrzedmiotu.LODZ);
        this.nazwaLodzi = nazwaLodzi;
    }

    @Override
    public String pelneInfo() {
        return super.pelneInfo() +
                "\n\tnazwa łodzi: " + nazwaLodzi;
    }
}

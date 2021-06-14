public class PojazdSamochodMiejski extends Pojazd {
    private boolean maSkladanyDach;

    public PojazdSamochodMiejski(int powierzchnia, String marka, boolean isElektryczny, int mocSilnikaKM, boolean maSkladanyDach) {
        super(powierzchnia, marka, isElektryczny, mocSilnikaKM, TypPrzedmiotu.SAMOCHOD_MIEJSKI);
        this.maSkladanyDach = maSkladanyDach;
    }

    public PojazdSamochodMiejski(int dlugosc, int szerokosc, String marka, boolean isElektryczny, int mocSilnikaKM, boolean maSkladanyDach) {
        super(dlugosc, szerokosc, marka, isElektryczny, mocSilnikaKM, TypPrzedmiotu.SAMOCHOD_MIEJSKI);
        this.maSkladanyDach = maSkladanyDach;
    }

    @Override
    public String pelneInfo() {
        return super.pelneInfo() +
                "\n\tskładany dach: " + (maSkladanyDach? "TAK" : "NIE");
    }
}

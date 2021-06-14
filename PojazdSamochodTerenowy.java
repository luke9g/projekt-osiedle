public class PojazdSamochodTerenowy extends Pojazd {
    private boolean maKoloZapasowe;

    public PojazdSamochodTerenowy(int powierzchnia, String marka, boolean isElektryczny, int mocSilnikaKM, boolean maKoloZapasowe) {
        super(powierzchnia, marka, isElektryczny, mocSilnikaKM, TypPrzedmiotu.SAMOCHOD_TERENOWY);
        this.maKoloZapasowe = maKoloZapasowe;
    }

    public PojazdSamochodTerenowy(int dlugosc, int szerokosc, String marka, boolean isElektryczny, int mocSilnikaKM, boolean maKoloZapasowe) {
        super(dlugosc, szerokosc, marka, isElektryczny, mocSilnikaKM, TypPrzedmiotu.SAMOCHOD_TERENOWY);
        this.maKoloZapasowe = maKoloZapasowe;
    }

    @Override
    public String pelneInfo() {
        return super.pelneInfo() +
                "\n\tkoło zapasowe: " + (maKoloZapasowe? "TAK" : "NIE");
    }
}

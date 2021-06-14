public class PojazdAmfibia extends Pojazd {
    private final boolean maGasienice;

    public PojazdAmfibia(int powierzchnia, String marka, boolean isElektryczny, int mocSilnikaKM, boolean maGasienice) {
        super(powierzchnia, marka, isElektryczny, mocSilnikaKM, TypPrzedmiotu.AMFIBIA);
        this.maGasienice = maGasienice;
    }

    public PojazdAmfibia(int dlugosc, int szerokosc, String marka, boolean isElektryczny, int mocSilnikaKM, boolean maGasienice) {
        super(dlugosc, szerokosc, marka, isElektryczny, mocSilnikaKM, TypPrzedmiotu.AMFIBIA);
        this.maGasienice = maGasienice;
    }

    @Override
    public String pelneInfo() {
        return super.pelneInfo() +
                "\n\tma gąsienice: " + (maGasienice? "TAK" : "NIE");
    }
}

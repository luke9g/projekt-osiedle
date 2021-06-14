public class PojazdMotocykl extends Pojazd {
    private boolean isSportowy;

    public PojazdMotocykl(int powierzchnia, String marka, boolean isElektryczny, int mocSilnikaKM, boolean isSportowy) {
        super(powierzchnia, marka, isElektryczny, mocSilnikaKM, TypPrzedmiotu.MOTOCYKL);
        this.isSportowy = isSportowy;
    }

    public PojazdMotocykl(int dlugosc, int szerokosc, String marka, boolean isElektryczny, int mocSilnikaKM, boolean isSportowy) {
        super(dlugosc, szerokosc, marka, isElektryczny, mocSilnikaKM, TypPrzedmiotu.MOTOCYKL);
        this.isSportowy = isSportowy;
    }

    @Override
    public String pelneInfo() {
        return super.pelneInfo() +
                "\n\tmodel sportowy: " + (isSportowy? "TAK" : "NIE");
    }
}

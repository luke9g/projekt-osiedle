public abstract class Pomieszczenie {
    public static final int OKRES_NAJMU = 30;  // [dni] (podczas testów programu warto zmniejszyć nieco tą wartość do ok. 10)

    private int powierzchnia;
    private int kubatura;
    private final String id;
    private final TypPomieszczenia typ;
    private final int nrBloku;
    private Osoba najemca;
    private int dzienRozpoczeciaNajmu;
    private int dzienZakonczeniaNajmu;
    private String dataRozpoczeciaNajmu;
    private String dataZakonczeniaNajmu;

    public Pomieszczenie(int objetosc, int domyslnaWysokosc, String id, TypPomieszczenia typ, int nrBloku) {
        this(id, typ, nrBloku);
        powierzchnia = objetosc / domyslnaWysokosc;
        kubatura = objetosc;
    }

    public Pomieszczenie(int dlugosc, int szerokosc, int wysokosc, String id, TypPomieszczenia typ, int nrBloku) {
        this(id, typ, nrBloku);
        powierzchnia = dlugosc * szerokosc;
        kubatura = powierzchnia * wysokosc;
    }

    private Pomieszczenie(String id, TypPomieszczenia typ, int nrBloku) {
        this.id = id;
        this.typ = typ;
        this.nrBloku = nrBloku;
        dzienRozpoczeciaNajmu = -1;
        dzienZakonczeniaNajmu = -1;
        dataRozpoczeciaNajmu = null;
        dataZakonczeniaNajmu = null;
    }

    public void setNajemca(Osoba najemca) throws ProblematicTenantException {
        if (najemca.getLiczbaZadluzen() > 3) {
            throw new ProblematicTenantException(najemca);
        }
        this.najemca = najemca;
        dzienRozpoczeciaNajmu = Zegar.getDzien();
        dzienZakonczeniaNajmu = dzienRozpoczeciaNajmu + OKRES_NAJMU;
        dataRozpoczeciaNajmu = Zegar.obliczDate(dzienRozpoczeciaNajmu);
        dataZakonczeniaNajmu = Zegar.obliczDate(dzienZakonczeniaNajmu);
    }

    public Osoba getNajemca() {
        return najemca;
    }

    public String getId() {
        return id;
    }

    public TypPomieszczenia getTyp() {
        return typ;
    }

    public int getPowierzchnia() {
        return powierzchnia;
    }

    public int getDzienZakonczeniaNajmu() {
        return dzienZakonczeniaNajmu;
    }

    public String getDataZakonczeniaNajmu() {
        return dataZakonczeniaNajmu;
    }

    public void przedluzNajem(int liczbaDni) {
        dzienZakonczeniaNajmu += liczbaDni;
        dataZakonczeniaNajmu = Zegar.obliczDate(dzienZakonczeniaNajmu);
    }

    public void zakonczNajem() {
        wyczyscPomieszczenie();
        this.najemca = null;
        dzienRozpoczeciaNajmu = -1;
        dzienZakonczeniaNajmu = -1;
        dataRozpoczeciaNajmu = null;
        dataZakonczeniaNajmu = null;
    }

    abstract protected void wyczyscPomieszczenie();

    public String najmInfo() {
        return this.toString() + " - data rozpoczęcia najmu: " + dataRozpoczeciaNajmu +
                ", data zakończenia najmu: " + dataZakonczeniaNajmu +
                (Zegar.getDzien() > getDzienZakonczeniaNajmu() ? ", MOŻNA PRZEDŁUŻYC/ZAKOŃCZYĆ" : "");
    }

    public String pelneInfo() {
        return this.toString() +
                "\n\tnumer bloku: " + nrBloku +
                "\n\tpowierzchnia: " + powierzchnia + " m2" +
                "\n\tkubatura: " + kubatura + " m3" +
                "\n\tnajemca: " + (najemca != null ? najemca : "BRAK") +
                "\n\tdata rozpoczęcia najmu: " + (najemca != null ? dataRozpoczeciaNajmu : "NIE DOTYCZY") +
                "\n\tdata zakończenia najmu: " + (najemca != null ? dataZakonczeniaNajmu : "NIE DOTYCZY");
    }

    public String krotkieInfo() {
        return typ + " " + id + " - najemca: " + (najemca != null ? najemca : "BRAK");
    }

    @Override
    public String toString() {
        return typ + " " + id;
    }
}

import java.util.ArrayList;

public class Mieszkanie extends Pomieszczenie {
    private static int generator = 0;

    private final ArrayList<Osoba> lokatorzy;

    public Mieszkanie(int objetosc, int nrBloku) {
        super(objetosc, 3, "m" + ++generator, TypPomieszczenia.MIESZKANIE, nrBloku);
        lokatorzy = new ArrayList<>();
    }

    public Mieszkanie(int dlugosc, int szerokosc, int wysokosc , int nrBloku) {
        super(dlugosc, szerokosc, wysokosc, "m" + ++generator, TypPomieszczenia.MIESZKANIE, nrBloku);
        lokatorzy = new ArrayList<>();
    }

    public void dodajLokatora(Osoba o) {
        lokatorzy.add(o);
    }

    public void usunLokatora(Osoba o) {
        lokatorzy.remove(o);
    }

    public boolean czyLokator(Osoba o) {
        return lokatorzy.contains(o);
    }

    public ArrayList<Osoba> getLokatorzy() {
        return lokatorzy;
    }

    @Override
    protected void wyczyscPomieszczenie() {
        for (Osoba lokator : lokatorzy) {
            lokator.usunZamieszkanie(this);
        }
        lokatorzy.clear();
    }

    @Override
    public String pelneInfo() {
        return super.pelneInfo() +
                "\n\tlokatorzy: " + (lokatorzy.size() != 0 ? lokatorzy : "BRAK");
    }

    @Override
    public String krotkieInfo() {
        return super.krotkieInfo() + ", lokatorzy: " + (lokatorzy.size() != 0 ? lokatorzy : "BRAK");
    }
}

import java.util.ArrayList;

public class Blok {
    private static int generator = 0;

    private final int numer;
    private final ArrayList<Pomieszczenie> pomieszczenia;

    public Blok(int mieszkaniaNaBlok, int miejscaParkingoweNaBlok) {
        numer = ++generator;
        pomieszczenia = new ArrayList<>();

        // tworzenie mieszkań
        for (int i = 0; i < mieszkaniaNaBlok; i++) {
            int randomDlugosc = losujLiczbeZPrzedzialu(10, 14);
            int randomSzerokosc = losujLiczbeZPrzedzialu(7, 10);
            Mieszkanie m = new Mieszkanie(randomDlugosc, randomSzerokosc, 3, numer);
            pomieszczenia.add(m);
        }

        // tworzenie miejsc parkingowych
        for (int i = 0; i < miejscaParkingoweNaBlok; i++) {
            int randomDlugosc = losujLiczbeZPrzedzialu(4, 5);
            int randomSzerokosc = losujLiczbeZPrzedzialu(3, 4);
            MiejsceParkingowe p = new MiejsceParkingowe(randomDlugosc, randomSzerokosc, 5, numer);
            pomieszczenia.add(p);
        }
    }

    private int losujLiczbeZPrzedzialu(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        return (int) Math.floor((Math.random() * (max - min + 1) + min));
    }

    public ArrayList<Pomieszczenie> getPomieszczenia() {
        return pomieszczenia;
    }

    public void wyswietlPomieszczenia() {
        System.out.println("----- Blok " + numer + " -----");
        pomieszczenia.forEach((pom) -> System.out.println(pom.krotkieInfo()));
    }

    public void wyswietlPomieszczenia(boolean zajete) {
        System.out.println("----- Blok " + numer + " -----");
        if (zajete) {
            pomieszczenia.stream()
                    .filter((pom) -> pom.getNajemca() != null)
                    .forEach((pom) -> System.out.println(pom.krotkieInfo()));
        } else {
            pomieszczenia.stream()
                    .filter((pom) -> pom.getNajemca() == null)
                    .forEach((pom) -> System.out.println(pom.krotkieInfo()));
        }
    }
}

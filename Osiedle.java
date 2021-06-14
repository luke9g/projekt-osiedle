import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;

public class Osiedle {
    private String nazwa;
    private final ArrayList<Blok> bloki;

    public Osiedle(String nazwa, int iloscBlokow, int mieszkaniaNaBlok, int miejscaParkingoweNaBlok) {
        this.nazwa = nazwa;
        bloki = new ArrayList<>();

        // tworzenie bloków
        for (int i = 0; i < iloscBlokow; i++) {
            Blok b = new Blok(mieszkaniaNaBlok, miejscaParkingoweNaBlok);
            bloki.add(b);
        }
    }

    public String getNazwa() {
        return nazwa;
    }

    public void rozpocznijNajem(Osoba o, String idPomieszczenia) {
        Pomieszczenie pom = getPomieszczenieById(idPomieszczenia);
        if (pom == null) {
            System.out.println("Nie ma pomieszczenia o takim id!");
            return;
        }
        if (pom.getNajemca() == o) {
            System.out.println("Już wynajmujesz to pomieszczenie!");
            return;
        } else if (pom.getNajemca() != null) {
            System.out.println("Już ktoś inny wynajmuje to pomieszczenie!");
            return;
        }
        if (o.getLiczbaNajmow() >= 5) {
            System.out.println("Wyczerpałeś już limit najmów!");
            return;
        }
        if (!o.mozeWynajacMiejsceParkingowe() && pom.getTyp() == TypPomieszczenia.MIEJSCE_PARKINGOWE) {
            System.out.println("Nie można wynająć miejsca parkingowego, jeśli nie wynajmujesz żadnego mieszkania!");
            return;
        }
        try {
            pom.setNajemca(o);
        } catch (ProblematicTenantException e) {
            System.err.println(e.getMessage());
            return;
        }
        o.dodajNajem(pom);
        System.out.println("Zostałeś najemcą " + pom + ".");
    }

    private Pomieszczenie getPomieszczenieById(String idPomieszczenia) {
        for (Blok blok : bloki) {
            for (Pomieszczenie pom : blok.getPomieszczenia()) {
                if (pom.getId().equals(idPomieszczenia)) {
                    return pom;
                }
            }
        }
        return null;
    }

    public void wyswietlPomieszczenie(String idPomieszczenia) {
        Pomieszczenie pom = getPomieszczenieById(idPomieszczenia);
        if (pom == null) {
            System.out.println("Nie ma pomieszczenia o takim id!");
            return;
        }
        System.out.println(pom.pelneInfo());
    }

    public void wyswietlPomieszczenia() {
        bloki.forEach(Blok::wyswietlPomieszczenia);
    }

    public void wyswietlPomieszczenia(boolean zajete) {
        bloki.forEach((b) -> b.wyswietlPomieszczenia(zajete));
    }

    public void sprawdzStanNajmow(Osoba uzytkownik) {
        for (Blok blok : bloki) {
            for (Pomieszczenie pom : blok.getPomieszczenia()) {
                Osoba najemca = pom.getNajemca();
                if (najemca != null && Zegar.getDzien() > pom.getDzienZakonczeniaNajmu()) {
                    if (Zegar.getDzien() <= pom.getDzienZakonczeniaNajmu() + Pomieszczenie.OKRES_NAJMU) {
                        if (!najemca.getZadluzenia().contains(new File(pom.toString()))) {
                            najemca.dodajZadluzenie(pom);
                            if (najemca == uzytkownik) {
                                System.out.println(pom + " - możesz już przedłużyć lub zakończyć najem tego " +
                                        "pomieszczenia, masz na to " + Pomieszczenie.OKRES_NAJMU + " dni.");
                            }
                        }
                    } else {
                        if (najemca == uzytkownik) {
                            System.err.println(pom + " - nie przedłużyłeś ani nie zakończyłeś najmu w ciągu " +
                                    Pomieszczenie.OKRES_NAJMU + " dni.");
                        }
                        if (pom.getTyp() == TypPomieszczenia.MIEJSCE_PARKINGOWE) {
                            MiejsceParkingowe p = (MiejsceParkingowe) pom;
                            Przedmiot sprzedanyPojazd = p.sprzedajPojazd();
                            if (sprzedanyPojazd != null) {
                                p.usunPrzedmiot(sprzedanyPojazd);
                                sprzedanyPojazd.setMiejsce(null);
                                p.przedluzNajem(2 * Pomieszczenie.OKRES_NAJMU);
                                if (najemca == uzytkownik) {
                                    System.err.println("\tKomornik sprzedał Twój pojazd " + sprzedanyPojazd +
                                            ". Koszt sprzedaży pokrył najem tego pomieszczenia do dnia " +
                                            pom.getDataZakonczeniaNajmu() + ".");
                                }
                            } else {
                                najemca.usunNajem(pom);
                                pom.zakonczNajem();
                                if (najemca == uzytkownik) {
                                    System.err.println("\tNajem tego pomieszczenia został cofnięty." +
                                            " Jeśli przechowywane były w nim jakieś przedmioty, to zostały usunięte.");
                                }
                            }
                        } else {
                            najemca.usunNajem(pom);
                            pom.zakonczNajem();
                            if (najemca == uzytkownik) {
                                System.err.println("\tNajem tego pomieszczenia został cofnięty." +
                                        " Jeśli mieszkali w nim jacyś lokatorzy, to zostali eksmitowani.");
                            }
                        }
                    }
                }
            }
        }
    }

    public void zapiszStanOsiedla(String nazwaPliku) {
        System.out.println("Trwa zapisywanie...");
        try {
            PrintStream ps = new PrintStream(nazwaPliku, StandardCharsets.UTF_8);
            ps.println("Osiedle " + nazwa + " - stan na dzień " + Zegar.getData());

            ArrayList<Pomieszczenie> pomieszczenia = new ArrayList<>();  // pomieszczenia osiedla
            ArrayList<Osoba> mieszkancy = new ArrayList<>();  // mieszkańcy osiedla (najemcy i lokatorzy)
            ArrayList<Przedmiot> przedmioty = new ArrayList<>();  // przedmioty znajdujące się na osiedlu

            for (Blok blok : bloki) {
                for (Pomieszczenie pom : blok.getPomieszczenia()) {
                    pomieszczenia.add(pom);
                    Osoba najemca = pom.getNajemca();
                    if (najemca != null && !mieszkancy.contains(najemca)) {
                        mieszkancy.add(najemca);
                    }
                    if (pom.getTyp() == TypPomieszczenia.MIESZKANIE) {
                        Mieszkanie m = (Mieszkanie) pom;
                        ArrayList<Osoba> lokatorzy = m.getLokatorzy();
                        for (Osoba lokator : lokatorzy) {
                            if (!mieszkancy.contains(lokator)) {
                                mieszkancy.add(lokator);
                            }
                        }
                    } else if (pom.getTyp() == TypPomieszczenia.MIEJSCE_PARKINGOWE) {
                        MiejsceParkingowe p = (MiejsceParkingowe) pom;
                        przedmioty.addAll(p.getPrzedmioty());
                    }
                }
            }

            // pomieszczenia posortowane rosnąco po powierzchni (w tym zawartość miejsc parkingowych jest sortowana
            // podczas dodawania/usuwania przedmiotów - malejąco po rozmiarze, a jeśli jest taki sam to alfabetycznie
            // po nazwie)
            ps.println("\n---------- POMIESZCZENIA ----------");
            pomieszczenia.sort(Comparator.comparingInt(Pomieszczenie::getPowierzchnia));
            pomieszczenia.forEach((pom) -> ps.println(pom.pelneInfo()));

            // mieszkancy posortowani rosnąco po numerze (liczba w id)
            ps.println("\n---------- MIESZKANCY ----------");
            mieszkancy.sort(Comparator.comparingInt(Osoba::getNumer));
            mieszkancy.forEach((mieszkaniec) -> ps.println(mieszkaniec.pelneInfo()));

            // przedmioty posortowane rosnąco po id (liczba w id)
            ps.println("\n---------- PRZEDMIOTY ----------");
            przedmioty.sort(Comparator.comparingInt(Przedmiot::getNumer));
            przedmioty.forEach((prz) -> ps.println(prz.pelneInfo()));

            ps.close();
            System.out.println("Pomyślnie zapisano stan osiedla do pliku \"" + nazwaPliku + "\".");
        } catch (IOException e) {
            System.err.println("Wystąpił BŁĄD podczas zapisywania pliku!");
            e.printStackTrace();
        }
    }
}

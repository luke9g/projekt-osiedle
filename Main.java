import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // tworzenie osiedla
        Osiedle osiedle = new Osiedle("Słoneczne", 3, 4, 4);

        // tworzenie osób
        ArrayList<Osoba> osoby = generateOsoby();

        // tworzenie wstępnie przydzielonych najmów
        przydzielNajmy(osiedle, osoby);

        // tworzenie przedmiotów
        ArrayList<Przedmiot> przedmioty = generatePrzedmioty();

        // tworzenie wstępnie umiejscowionych przedmiotów na miejscach parkingowych
        przydzielPrzedmioty(przedmioty, osoby);

        // wyświetlenie instrukcji obsługi programu
        wyswietlInstrukcje();

        // wstępny wybór osoby przez użytkownika
        Osoba wybranaOsoba = null;
        java.util.Scanner input = new java.util.Scanner(System.in); 

        System.out.println("\n----- Aby zakończyć program wpisz 'q' w dowolnym momencie. -----");
        System.out.println("\nOsiedle " + osiedle.getNazwa() + " - zapraszamy!");
        System.out.println("Na początek zapoznaj się z instrukcją programu i wybierz, którą osobą będziesz, podając teraz jej numer (np. o3). Dostępne osoby: ");
        osoby.forEach((o) -> System.out.println("\t" + o));

        while (true) {
            System.out.println("Wybieram osobę: ");
            String polecenie = input.nextLine();

            if (polecenie.equals("q")) {
                return;
            }
            for (Osoba o : osoby) {
                if (o.getId().equals(polecenie)) {
                    wybranaOsoba = o;
                    System.out.println("Od teraz jesteś osobą: " + wybranaOsoba + ".");
                    break;
                }
            }
            if (wybranaOsoba != null) {
                break;
            }
            System.out.println("Nie ma takiej osoby. Spróbuj jeszcze raz.");
        }

        // ropoczęcie odmierzania czasu
        Zegar zegar = new Zegar(osiedle, wybranaOsoba);
        zegar.start();
        System.out.println("Dziś jest " + Zegar.getData() + ". Zegar rozpoczął odliczanie.");

        // główna pętla programu
        while (true) {
            System.out.println("\n" + wybranaOsoba + " - polecenie: ");
            String polecenie = input.nextLine().strip();

            if (polecenie.equals("q")) {
                zegar.interrupt();
                return;
            } else if (polecenie.equals("ja")) {
                wybranaOsoba.wyswietlPelneInfo();
            } else if (polecenie.equals("data")) {
                System.out.println(Zegar.getData());
            } else if (polecenie.equals("zapisz")) {
                osiedle.zapiszStanOsiedla("osiedle.txt");

            } else if (polecenie.equals("osoby")) {
                osoby.forEach(Osoba::wyswietlKrotkieInfo);
            } else if (polecenie.matches("o[0-9]{1,4}")) {
                wyswietlOsobe(polecenie, osoby);
            } else if (polecenie.matches("zmien o[0-9]{1,4}")) {
                Osoba o = getOsobaById(polecenie.split(" ")[1], osoby);
                if (wybranaOsoba == o) {
                    System.out.println("Już jesteś tą osobą!");
                } else if (o != null) {
                    wybranaOsoba = o;
                    zegar.setUzytkownik(wybranaOsoba);
                    System.out.println("Od teraz jesteś osobą: " + wybranaOsoba + ".");
                } else {
                    System.out.println("Nie ma osoby o takim id!");
                }

            } else if (polecenie.equals("pom")) {
                osiedle.wyswietlPomieszczenia();
            } else if (polecenie.equals("pom wolne")) {
                osiedle.wyswietlPomieszczenia(false);
            } else if (polecenie.equals("pom zajete")) {
                osiedle.wyswietlPomieszczenia(true);
            } else if (polecenie.matches("[mp][0-9]{1,4}")) {
                osiedle.wyswietlPomieszczenie(polecenie);

            } else if (polecenie.equals("przedmioty")) {
                przedmioty.forEach((prz) -> System.out.println(prz.krotkieInfo()));
            } else if (polecenie.matches("prz[0-9]{1,4}")) {
                wyswietlPrzedmiot(polecenie, przedmioty);

            } else if (polecenie.matches("wynajmij [mp][0-9]{1,4}")) {
                osiedle.rozpocznijNajem(wybranaOsoba, polecenie.split(" ")[1]);
            } else if (polecenie.equals("najmy")) {
                wybranaOsoba.wyswietlNajmy();
            } else if (polecenie.matches("przedluz [mp][0-9]{1,4}")) {
                wybranaOsoba.przedluzNajem(polecenie.split(" ")[1]);
            } else if (polecenie.equals("przedluz")) {
                wybranaOsoba.przedluzWszystkie();
            } else if (polecenie.matches("anuluj [mp][0-9]{1,4}")) {
                wybranaOsoba.anulujNajem(polecenie.split(" ")[1]);
            } else if (polecenie.equals("anuluj")) {
                wybranaOsoba.anulujWszystkie();
            } else if (polecenie.matches("zamelduj o[0-9]{1,4} m[0-9]{1,4}")) {
                String[] slowa = polecenie.split(" ");
                wybranaOsoba.zameldujLokatora(getOsobaById(slowa[1], osoby), slowa[2]);
            } else if (polecenie.matches("wymelduj o[0-9]{1,4} m[0-9]{1,4}")) {
                String[] slowa = polecenie.split(" ");
                wybranaOsoba.wymeldujLokatora(getOsobaById(slowa[1], osoby), slowa[2]);
            } else if (polecenie.matches("wloz prz[0-9]{1,4} p[0-9]{1,4}")) {
                String[] slowa = polecenie.split(" ");
                wybranaOsoba.wlozPrzedmiot(getPrzedmiotById(slowa[1], przedmioty), slowa[2]);
            } else if (polecenie.matches("wyjmij prz[0-9]{1,4} p[0-9]{1,4}")) {
                String[] slowa = polecenie.split(" ");
                wybranaOsoba.wyjmijPrzedmiot(getPrzedmiotById(slowa[1], przedmioty), slowa[2]);
            } else {
                System.out.println("Nie rozpoznano polecenia. Spróbuj jeszcze raz.");
            }
        }
    }

    public static ArrayList<Osoba> generateOsoby() {
        ArrayList<Osoba> osoby = new ArrayList<>();

        osoby.add(new Osoba("Harry", "Potter", "83112462832",
                "ul. Lipowa 2", "24.11.1983"));
        osoby.add(new Osoba("Ronald", "Weasley", "83112984031",
                "ul. Długa 8/2", "29.11.1983"));
        osoby.add(new Osoba("Hermiona", "Granger", "83022873940",
                "ul. Kwiatowa 12", "28.02.1983"));
        osoby.add(new Osoba("Marian", "Ziemniak", "79010187320",
                "ul. Wiejska 1", "01.01.1979"));
        osoby.add(new Osoba("Justyna", "Ziemniak", "99120678004",
                "ul. Wiejska 1", "06.12.1999"));
        osoby.add(new Osoba("Henryka", "Grządka", "73031189361",
                "ul. Wiejska 5", "11.03.1973"));
        osoby.add(new Osoba("Robert", "Makłowicz", "63081290207",
                "ul. Smaczna 10/15", "12.08.1963"));

        return osoby;
    }

    public static void przydzielNajmy(Osiedle osiedle, ArrayList<Osoba> osoby) {
        osiedle.rozpocznijNajem(osoby.get(0), "m2");
        osiedle.rozpocznijNajem(osoby.get(0), "p1");
        osiedle.rozpocznijNajem(osoby.get(0), "p2");
        osiedle.rozpocznijNajem(osoby.get(0), "p3");
        osoby.get(0).zameldujLokatora(osoby.get(1), "m2");
        osoby.get(0).zameldujLokatora(osoby.get(2), "m2");
        osiedle.rozpocznijNajem(osoby.get(3), "m7");
        osiedle.rozpocznijNajem(osoby.get(3), "p7");
        osoby.get(3).zameldujLokatora(osoby.get(4), "m7");
        osiedle.rozpocznijNajem(osoby.get(5), "m12");
        osiedle.rozpocznijNajem(osoby.get(5), "p10");
        osiedle.rozpocznijNajem(osoby.get(5), "p11");
    }

    public static ArrayList<Przedmiot> generatePrzedmioty() {
        ArrayList<Przedmiot> przedmioty = new ArrayList<>();

        przedmioty.add(new Przedmiot(5, "stóg siana"));
        przedmioty.add(new Przedmiot(2, "stos opon"));
        przedmioty.add(new Przedmiot(1, "reflektor budowlany"));
        przedmioty.add(new Przedmiot(10, "kontener"));
        przedmioty.add(new Przedmiot(3, "rury wodociągowe"));
        przedmioty.add(new Przedmiot(4, "pręty stalowe"));
        przedmioty.add(new PojazdMotocykl(3, "Yamaha", false, 70,
                true));
        przedmioty.add(new PojazdSamochodMiejski(7, "Tesla", true, 110,
                false));
        przedmioty.add(new PojazdLodz(10, "Lagoon", false, 40,
                "Królowa Jezior"));
        przedmioty.add(new PojazdAmfibia(12, "PTS-M", false, 50,
                true));
        przedmioty.add(new PojazdSamochodTerenowy(8, "Jeep", false, 110,
                false));

        return przedmioty;
    }

    public static void przydzielPrzedmioty(ArrayList<Przedmiot> przedmioty, ArrayList<Osoba> osoby) {
        osoby.get(0).wlozPrzedmiot(przedmioty.get(1), "p3");
        osoby.get(0).wlozPrzedmiot(przedmioty.get(3), "p3");
        osoby.get(0).wlozPrzedmiot(przedmioty.get(6), "p1");
        osoby.get(3).wlozPrzedmiot(przedmioty.get(10), "p7");
        osoby.get(5).wlozPrzedmiot(przedmioty.get(0), "p10");
    }

    public static void wyswietlInstrukcje() {
        System.out.println("\n----- Instrukcja obsługi programu: -----");
        System.out.println("* w konsoli wpisuj wybrane polecenie (po jednym na raz), listę dostępnych poleceń znajdziesz poniżej");
        System.out.println("* jeśli w poleceniu jest mowa o <id osoby/pomieszczenia/przedmiotu> pamiętaj, że ma ono postać: ");
        System.out.println("\t- dla osoby: o<numer> [np. o3]");
        System.out.println("\t- dla mieszkania: m<numer> [np. m11]");
        System.out.println("\t- dla miejsca parkingowego: p<numer> [np. p1]");
        System.out.println("\t- dla przedmiotu: prz<numer> [np. prz5]");
        System.out.println("* jeśli polecenie składa się z kilku wyrazów, pamiętaj, aby je oddzielać pojedynczą spacją");
        System.out.println("* czas w programie jest przyspieszony: co 5 sekund jest przesuwany o 1 dzień do przodu");
        System.out.println("* możesz mieć max. 5 najmów");
        System.out.println("* rozpoczęcie najmu miejsca parkingowego jest możliwe tylko jeśli aktualnie wynajmujesz jakieś mieszkanie");
        System.out.println("* najem pomieszczeń trwa " + Pomieszczenie.OKRES_NAJMU + " dni, przedłużenie lub anulowanie najmu jest równoznaczne z zapłatą za najem " +
                "\n  i jest możliwe od dnia zakończenia najmu + max. " + Pomieszczenie.OKRES_NAJMU + " dni");
        System.out.println("* jeśli nie przedłużysz lub anulujesz najmu samodzielnie, to dostaniesz zadłużenie, a Twój najem zostanie cofnięty " +
                "\n  (chyba, że jest to miejsce parkingowe i posiadasz tam pojazd - wówczas komornik go sprzeda pokrywając koszt najmu, ale zadłużenie pozostanie)");
        System.out.println("* jeśli będziesz miał powyżej 3 zadłużeń, nie będziesz już mógł wynająć żadnego pomieszczenia");

        System.out.println("\nDostępne polecenia w programie (po wybraniu osoby):");
        System.out.println("q   - kończy program");
        System.out.println("ja   - wyświetla informacje o Tobie");
        System.out.println("data   - wyświetla aktualną datę na osiedlu");
        System.out.println("zapisz   - zapisuje aktualny stan osiedla do pliku tekstowego");

        System.out.println("\nosoby   - wyświetla listę osób w programie");
        System.out.println("<id osoby>   - wyświetla wszystkie informację o danej osobie");
        System.out.println("zmien <id osoby>   - zmienia aktualnie wybraną osobę na nową o podanym id");

        System.out.println("\npom   - wyświetla listę wszystkich pomieszczeń na osiedlu");
        System.out.println("pom wolne   - wyświetla listę wolnych pomieszczeń na osiedlu");
        System.out.println("pom zajete   - wyświetla listę zajętych pomieszczeń na osiedlu (tzn. tych, które mają najemcę)");
        System.out.println("<id pomieszczenia>   - wyświetla wszystkie informacje o danym pomieszczeniu");

        System.out.println("\nprzedmioty   - wyświetla listę przedmiotów w programie");
        System.out.println("<id przedmiotu>   - wyświetla wszystkie informacje o danym przedmiocie");

        System.out.println("\nwynajmij <id pomieszczenia>   - wynajmuje dane pomieszczenie, jeśli jest wolne");
        System.out.println("najmy   - wyświetla listę Twoich aktualnie wynajętych pomieszczeń, wraz z datą ropoczęcia/zakończenia");
        System.out.println("przedluz <id pomieszczenia>   - przedłuża najem danego pomieszczenia które wynajmujesz, o ile skończył mu się już okres najmu i nie upłynął następny");
        System.out.println("przedluz   - przedłuża najmy wszystkich Twoich wynajętych pomieszczeń, o ile skończyły im się już okresy najmu i nie upłynęły następne");
        System.out.println("anuluj <id pomieszczenia>   - anuluje najem danego pomieszczenia które wynajmujesz, o ile skończył mu się już okres najmu i nie upłynął następny");
        System.out.println("anuluj   - anuluje najmy wszystkich Twoich wynajętych pomieszczeń, o ile skończyły im się już okresy najmu i nie upłynęły następne");
        System.out.println("zamelduj <id osoby> <id mieszkania>   - zameldowuje daną osobę do mieszkania, które wynajmujesz");
        System.out.println("wymelduj <id osoby> <id mieszkania>   - wymeldowuje daną osobę z mieszkania, które wynajmujesz");
        System.out.println("wloz <id przedmiotu> <id miejsca parkingowego>   - wkłada dany przedmiot, jeśli nie jest zajęty, na miejsce parkingowe, które wynajmujesz");
        System.out.println("wyjmij <id przedmiotu> <id miejsca parkingowego>   - wyjmuje dany przedmiot z miejsca parkingowego, które wynajmujesz");
    }

    public static Osoba getOsobaById(String idOsoby, ArrayList<Osoba> osoby) {
        for (Osoba o : osoby) {
            if (o.getId().equals(idOsoby)) {
                return o;
            }
        }
        return null;
    }

    public static void wyswietlOsobe(String idOsoby, ArrayList<Osoba> osoby) {
        Osoba o = getOsobaById(idOsoby, osoby);
        if (o == null) {
            System.out.println("Nie ma osoby o takim id!");
            return;
        }
        System.out.println(o.pelneInfo());
    }

    public static Przedmiot getPrzedmiotById(String idPrzedmiotu, ArrayList<Przedmiot> przedmioty) {
        for (Przedmiot prz : przedmioty) {
            if (prz.getId().equals(idPrzedmiotu)) {
                return prz;
            }
        }
        return null;
    }

    public static void wyswietlPrzedmiot(String idPrzedmiotu, ArrayList<Przedmiot> przedmioty) {
        Przedmiot prz = getPrzedmiotById(idPrzedmiotu, przedmioty);
        if (prz == null) {
            System.out.println("Nie ma przedmiotu o takim id!");
            return;
        }
        System.out.println(prz.pelneInfo());
    }
}

/*
Podczas pisania programu założyłem, że dana osoba może być zameldowana jako lokator w wielu mieszkaniach.
Natomiast przedmiot może być przypisany do tylko jednego miejsca parkingowego.

Uruchamianie programu w konsoli poza IntelliJ - komendy "javac -encoding utf-8 Main.java" i "java Main".
*/

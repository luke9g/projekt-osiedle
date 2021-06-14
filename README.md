## Projekt Osiedle

Program konsolowy mający symulować osiedle. Użytkownik może wynajmować pomieszczenia i wykonywać różne
akcje z tym związane - np. dodawać/usuwać lokatorów do swoich mieszkań, lub wkładać/wyjmować przedmioty ze swoich miejsc
parkingowych. Na początku programu tworzone są przykładowe osoby i wstępnie przydzielane najmy. Po wybraniu osoby przez
użytkownika rozpoczyna się upływ czasu (w odrębnym wątku).

Program to projekt studencki, który powstał na zaliczenie na studiach (semestr II). Dokładna treść zadania i wymagania
projektu znajdują się w pliku "projekt.png".

### Uruchomienie
_Projekt wykorzystuje Javę 11._

1. ściągnąć pliki do wybranego folderu
2. przejść w konsoli do folderu z programem
3. `javac -encoding utf-8 Main.java`
4. `java Main`

### Instrukcja obsługi programu
* w konsoli wpisuj wybrane polecenie (po jednym na raz), listę dostępnych poleceń znajdziesz poniżej
* jeśli w poleceniu jest mowa o `<id osoby>`, `<id pomieszczenia>`, `<id przedmiotu>` pamiętaj, że ma ono postać:
    - dla osoby: `o<numer>` (np. `o3`)
    - dla mieszkania: `m<numer>` (np. `m11`)
    - dla miejsca parkingowego: `p<numer>` (np.` p1`)
    - dla przedmiotu: `prz<numer>` (np. `prz5`)
* jeśli polecenie składa się z kilku wyrazów, pamiętaj, aby je oddzielać pojedynczą spacją
* czas w programie jest przyspieszony: co 5 sekund jest przesuwany o 1 dzień do przodu
* możesz mieć max. 5 najmów
* rozpoczęcie najmu miejsca parkingowego jest możliwe tylko jeśli aktualnie wynajmujesz jakieś mieszkanie
* najem pomieszczeń trwa 30 dni, przedłużenie lub anulowanie najmu jest równoznaczne z zapłatą za najem
  i jest możliwe od dnia zakończenia najmu + max. 30 dni
* jeśli nie przedłużysz lub anulujesz najmu samodzielnie, to dostaniesz zadłużenie, a Twój najem zostanie cofnięty
  (chyba, że jest to miejsce parkingowe i posiadasz tam pojazd - wówczas komornik go sprzeda pokrywając koszt najmu, 
  ale zadłużenie pozostanie)
* jeśli będziesz miał powyżej 3 zadłużeń, nie będziesz już mógł wynająć żadnego pomieszczenia

### Dostępne polecenia w programie (po wybraniu osoby):
* `q` - kończy program
* `ja` - wyświetla informacje o Tobie
* `data` - wyświetla aktualną datę na osiedlu
* `zapisz` - zapisuje aktualny stan osiedla do pliku tekstowego
  

* `osoby` - wyświetla listę osób w programie
* `<id osoby>` - wyświetla wszystkie informację o danej osobie
* `zmien <id osoby>` - zmienia aktualnie wybraną osobę na nową o podanym id


* `pom` - wyświetla listę wszystkich pomieszczeń na osiedlu
* `pom wolne` - wyświetla listę wolnych pomieszczeń na osiedlu
* `pom zajete` - wyświetla listę zajętych pomieszczeń na osiedlu (tzn. tych, które mają najemcę)
* `<id pomieszczenia>` - wyświetla wszystkie informacje o danym pomieszczeniu


* `przedmioty` - wyświetla listę przedmiotów w programie
* `<id przedmiotu>` - wyświetla wszystkie informacje o danym przedmiocie


* `wynajmij <id pomieszczenia>` - wynajmuje dane pomieszczenie, jeśli jest wolne
* `najmy` - wyświetla listę Twoich aktualnie wynajętych pomieszczeń, wraz z datą ropoczęcia/zakończenia
* `przedluz <id pomieszczenia>` - przedłuża najem danego pomieszczenia które wynajmujesz, o ile skończył mu się już
  okres najmu i nie upłynął następny
* `przedluz` - przedłuża najmy wszystkich Twoich wynajętych pomieszczeń, o ile skończyły im się już okresy najmu i nie
  upłynęły następne
* `anuluj <id pomieszczenia>` - anuluje najem danego pomieszczenia które wynajmujesz, o ile skończył mu się już okres
  najmu i nie upłynął następny
* `anuluj` - anuluje najmy wszystkich Twoich wynajętych pomieszczeń, o ile skończyły im się już okresy najmu i nie
  upłynęły następne
* `zamelduj <id osoby> <id mieszkania>` - zameldowuje daną osobę do mieszkania, które wynajmujesz
* `wymelduj <id osoby> <id mieszkania>` - wymeldowuje daną osobę z mieszkania, które wynajmujesz
* `wloz <id przedmiotu> <id miejsca parkingowego>` - wkłada dany przedmiot, jeśli nie jest zajęty, na miejsce
  parkingowe, które wynajmujesz
* `wyjmij <id przedmiotu> <id miejsca parkingowego>` - wyjmuje dany przedmiot z miejsca parkingowego, które wynajmujesz
  
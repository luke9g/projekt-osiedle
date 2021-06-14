public class TooManyThingsException extends Exception {
    public TooManyThingsException() {
        super("BŁĄD: za dużo przedmiotów - wyjmij jakieś w celu włożenia nowego!");
    }
}

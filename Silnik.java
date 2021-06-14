public class Silnik<T extends Number> {
    private final T mocKM;  // [konie mechaniczne]

    public Silnik(T mocKM) {
        this.mocKM = mocKM;
    }

    public T getMocKM() {
        return mocKM;
    }

    public double obliczMocKW() {
        return mocKM.doubleValue() * 0.73549875;  // [kilowaty]
    }
}

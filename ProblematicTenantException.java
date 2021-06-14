public class ProblematicTenantException extends Exception {
    public ProblematicTenantException(Osoba o) {
        super("BŁĄD: problematyczny najemca - osoba " + o + " posiada zadłużone najmy: " + o.getZadluzenia() + ".");
    }
}

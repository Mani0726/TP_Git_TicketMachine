package ticketmachine;

public class ExceptionInsertionNbNegatif extends RuntimeException {
    public ExceptionInsertionNbNegatif(String message) {
        super(message);
    }

    public ExceptionInsertionNbNegatif() {
        super("Insertion d'un nombre négatif");
    }
}

package cabInvoiceGenerator;

public class InvoiceServicesException extends Throwable {

    public ExceptionType type;

    public enum ExceptionType {
        INVALID_USER_ID;
    }

    public InvoiceServicesException(String type, ExceptionType name) {
        super(type);
        this.type = name;
    }
}

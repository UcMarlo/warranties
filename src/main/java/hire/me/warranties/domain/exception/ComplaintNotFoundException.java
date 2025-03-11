package hire.me.warranties.domain.exception;

public class ComplaintNotFoundException extends RuntimeException {
    public ComplaintNotFoundException(String message){
        super(message);
    }
}

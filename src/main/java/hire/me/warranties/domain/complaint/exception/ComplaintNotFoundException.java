package hire.me.warranties.domain.complaint.exception;

public class ComplaintNotFoundException extends RuntimeException {
    public ComplaintNotFoundException(String message){
        super(message);
    }
}

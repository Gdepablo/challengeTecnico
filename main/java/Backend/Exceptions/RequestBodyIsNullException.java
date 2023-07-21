package Backend.Exceptions;

public class RequestBodyIsNullException extends RuntimeException{

    public RequestBodyIsNullException(String message) {
        super(message);
    }
}

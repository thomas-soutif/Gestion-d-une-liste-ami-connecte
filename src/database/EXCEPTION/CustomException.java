package database.EXCEPTION;

public class CustomException extends Exception {

    private  final ErrorType code;
    public CustomException(ErrorType code){
        super();
        this.code = code;
    }

    public CustomException(String message, Throwable cause, ErrorType code){
        super(message,cause);
        this.code = code;
    }
    public CustomException(String message, ErrorType code){
        super(message);
        this.code = code;
    }

    public CustomException(Throwable cause, ErrorType code){
        super(cause);
        this.code = code;
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    public ErrorType getErrorType() {
        return this.code;
    }

}

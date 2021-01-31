package database.EXCEPTION;

public class FriendRequestException extends Exception {

    private final ErrorType code;
    public FriendRequestException(ErrorType code){
        super();
        this.code = code;
    }

    public FriendRequestException(String message, Throwable cause, ErrorType code){
        super(message,cause);
        this.code = code;
    }
    public FriendRequestException(String message, ErrorType code){
        super(message);
        this.code = code;
    }

    public FriendRequestException(Throwable cause, ErrorType code){
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

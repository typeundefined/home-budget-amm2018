package dsr.amm.homebudget.controller.exception;

/**
 * Created by knekrasov on 10/25/2018.
 */
public class ApiException extends RuntimeException {
    public ApiException() {
        super();
    }

    public ApiException(String s) {
        super(s);
    }

    public ApiException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ApiException(Throwable throwable) {
        super(throwable);
    }
}

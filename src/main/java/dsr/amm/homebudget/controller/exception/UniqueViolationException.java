package dsr.amm.homebudget.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by knekrasov on 10/25/2018.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UniqueViolationException extends ApiException {
    public UniqueViolationException(String s) {
        super(s);
    }
}

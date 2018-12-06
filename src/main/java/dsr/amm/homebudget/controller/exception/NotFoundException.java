package dsr.amm.homebudget.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by knekrasov on 10/26/2018.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends ApiException {
    public NotFoundException(String s) {
        super(s);
    }
}

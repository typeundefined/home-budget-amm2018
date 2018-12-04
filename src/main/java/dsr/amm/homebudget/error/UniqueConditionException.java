package dsr.amm.homebudget.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class UniqueConditionException extends RuntimeException{

    public UniqueConditionException(String msg) {
        super(msg);
    }
}

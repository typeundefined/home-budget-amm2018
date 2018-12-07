package dsr.amm.homebudget;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UniqueConditionException extends RuntimeException{

    public UniqueConditionException(String msg) {
        super(msg);
    }
}
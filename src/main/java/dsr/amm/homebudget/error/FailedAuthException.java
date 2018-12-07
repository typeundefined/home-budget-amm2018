package dsr.amm.homebudget.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class FailedAuthException extends AuthenticationException {
    public FailedAuthException(String msg){
        super(msg);
    }
}

package dsr.amm.homebudget.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Entity already exist")
public class EntityAlreadyExistException extends RuntimeException {
}

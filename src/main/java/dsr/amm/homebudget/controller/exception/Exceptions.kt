package dsr.amm.homebudget.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenException(s: String) : ApiException(s)

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(s: String) : ApiException(s)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UniqueViolationException(s: String) : ApiException(s)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class TransactionTypeNotSupported(s: String) : ApiException(s)

open class ApiException : RuntimeException {
    constructor() : super()
    constructor(s: String) : super(s)
    constructor(s: String, throwable: Throwable) : super(s, throwable)
    constructor(throwable: Throwable) : super(throwable)
}
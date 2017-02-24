package <%= appPackage %>.domain.repository.exception

/**
 * Created by deividi on 23/08/16.
 */
class ApiException : Throwable {

    val errorMessage: ErrorMessage

    constructor(errorMessage: ErrorMessage) : super(errorMessage.message) {
        this.errorMessage = errorMessage
    }

    constructor(errorMessage: ErrorMessage, error: Throwable) : super(errorMessage.message, error) {
        this.errorMessage = errorMessage
    }

    constructor(throwable: Throwable) : super(throwable) {
        errorMessage = ErrorMessage()
    }
}

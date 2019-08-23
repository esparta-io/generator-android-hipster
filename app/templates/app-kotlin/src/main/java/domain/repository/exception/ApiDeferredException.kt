package <%= appPackage %>.domain.repository.exception

class ApiDeferredException : Throwable {

    val errorMessage: DeferredErrorMessage

    constructor(errorMessage: DeferredErrorMessage) : super(errorMessage.detail) {
        this.errorMessage = errorMessage
    }

    constructor(errorMessage: DeferredErrorMessage, error: Throwable) : super(errorMessage.detail, error) {
        this.errorMessage = errorMessage
    }

    constructor(throwable: Throwable) : super(throwable) {
        errorMessage = DeferredErrorMessage()
    }
}

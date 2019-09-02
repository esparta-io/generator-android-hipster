package <%= appPackage %>.domain.repository.exception

class DeferredErrorMessage {

    var code: String? = null

    var title: String? = null

    var status: Int = 0

    var detail: String? = null

    var path: String? = null

    var type: ErrorType? = null

    var rawString: String? = null

    enum class ErrorType {
        NO_CONNECTION,
        SERVER_NOT_REACHABLE,
        SERVER_ERROR,
        INTERNAL_ERROR,
        AUTHENTICATION_ERROR
    }
}
package <%= appPackage %>.domain.repository.exception

/**
 * Created by deividi on 23/08/16.
 */
class ErrorMessage {

    var message: String? = null

    var description: String? = null

    var erro: String? = null

    var code: String? = null

    var httpCode: Int = 0

    var throwable: Throwable? = null

    var type: ErrorType? = null

    enum class ErrorType {
        NO_CONNECTION,
        SERVER_NOT_REACHABLE,
        SERVER_ERROR,
        INTERNAL_ERROR,
        AUTHENTICATION_ERROR
    }
}
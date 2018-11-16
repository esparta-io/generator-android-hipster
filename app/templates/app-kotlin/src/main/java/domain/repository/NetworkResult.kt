package <%= appPackage %>.domain.repository

/**
 *
 * Created by gmribas on 15/11/18.
 */
sealed class NetworkResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Throwable) : NetworkResult<Nothing>()
}
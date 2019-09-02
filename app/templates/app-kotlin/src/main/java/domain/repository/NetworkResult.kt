package <%= appPackage %>.domain.repository

sealed class NetworkResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Throwable) : NetworkResult<Nothing>()

    fun <T : Any> NetworkResult<T>.getOrThrow(throwable: Throwable? = null): T {
        return when (this) {
            is NetworkResult.Success -> data
            is NetworkResult.Error -> throw throwable ?: exception
        }
    }
}
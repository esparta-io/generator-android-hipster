package <%= appPackage %>.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import <%= appPackage %>.domain.repository.exception.ApiDeferredException
import <%= appPackage %>.domain.repository.exception.DeferredErrorMessage
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 *
 * Created by gmribas on 15/11/18.
 */
object ExtractDeferredErrorUtil {

    fun extractError(e: Exception): ApiDeferredException {
        val errorMessage = DeferredErrorMessage()

        when (e) {
            is HttpException -> {
                val string = e.response().errorBody()?.string()
                Timber.d("ExtractError raw String error $string")
                return ApiDeferredException(convertErrorMessage(string), e)
            }
            is SocketTimeoutException, is ConnectException -> errorMessage.type = DeferredErrorMessage.ErrorType.SERVER_NOT_REACHABLE
            is IOException -> errorMessage.type = DeferredErrorMessage.ErrorType.NO_CONNECTION
            else -> errorMessage.type = DeferredErrorMessage.ErrorType.INTERNAL_ERROR
        }

        return ApiDeferredException(errorMessage)
    }

    private fun convertErrorMessage(string: String?): DeferredErrorMessage {
        try {
            return if (string == null || string == "") {
                DeferredErrorMessage()
            } else {
                Gson().fromJson(string, DeferredErrorMessage::class.java)
            }
        } catch (e: JsonSyntaxException) {
            Timber.e("JsonSyntaxException convertErrorMessage", e)
            val error = DeferredErrorMessage()
            error.rawString = string
            return error
        }
    }
}
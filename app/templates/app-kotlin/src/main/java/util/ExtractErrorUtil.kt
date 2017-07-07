package <%= appPackage %>.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import <%= appPackage %>.domain.repository.exception.ApiException
import <%= appPackage %>.domain.repository.exception.ErrorMessage
import retrofit2.adapter.rxjava2.Result
import io.reactivex.Observable
import io.reactivex.Single
<% if (timber == true) { %>import timber.log.Timber<% } %>
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by esparta on 15/09/16.
 */
class ExtractErrorUtil<INCOMING_RESULT_TYPE, ERROR_RESULT_TYPE> {

    fun extractError(incomingResult: Result<INCOMING_RESULT_TYPE>): Observable<ERROR_RESULT_TYPE> {

        if (incomingResult.isError) {
            val errorMessage = ErrorMessage()
            errorMessage.httpCode = incomingResult.response()?.code() ?: 0

            when (incomingResult.error()) {
                is SocketTimeoutException,
                is ConnectException -> errorMessage.type = ErrorMessage.ErrorType.SERVER_NOT_REACHABLE
                is IOException -> errorMessage.type = ErrorMessage.ErrorType.NO_CONNECTION
                else -> errorMessage.type = ErrorMessage.ErrorType.INTERNAL_ERROR
            }

            errorMessage.throwable = incomingResult.error()
            return Observable.error<ERROR_RESULT_TYPE>(ApiException(errorMessage, incomingResult.error()!!))
        }

        try {
            val string = incomingResult.response()?.errorBody()?.string()

            <% if (timber == true) { %>Timber.d("ExtractErrorUtil raw String error $string")<% } else { %>Throwable(string).printStackTrace()<% } %>

            val errorMessage = convertErrorMessage(string)

            errorMessage.httpCode = incomingResult.response()?.code() ?: 0
            errorMessage.type = ErrorMessage.ErrorType.SERVER_ERROR
            if (incomingResult.error() == null) {
                return Observable.error(ApiException(errorMessage))
            } else {
                return Observable.error(ApiException(errorMessage, incomingResult.error()!!))
            }
        } catch (e: IOException) {
            <% if (timber == true) { %>Timber.e("IOException in Rest call:", e)<% } else { %>e.printStackTrace()<% } %>
            val errorMessage = ErrorMessage()
            errorMessage.type = ErrorMessage.ErrorType.INTERNAL_ERROR
            errorMessage.httpCode = incomingResult.response()?.code() ?: 0
            errorMessage.throwable = e

            return Observable.error<ERROR_RESULT_TYPE>(ApiException(errorMessage, incomingResult.error()!!))

        }

    }

    fun extractSingleError(incomingResult: Result<INCOMING_RESULT_TYPE>): Single<ERROR_RESULT_TYPE> {
        return extractError(incomingResult).singleOrError()
    }

    private fun convertErrorMessage(string: String?): ErrorMessage {
        try {
            if (string == null || string == "") {
                return ErrorMessage()
            }
            return Gson().fromJson(string, ErrorMessage::class.java)
        } catch (e: JsonSyntaxException) {
            <% if (timber == true) { %>Timber.e("JsonSyntaxException convertErrorMessage", e)<% } else { %>e.printStackTrace()<% } %>
            val error = ErrorMessage()
            error.message = string
            return error
        }
    }
}

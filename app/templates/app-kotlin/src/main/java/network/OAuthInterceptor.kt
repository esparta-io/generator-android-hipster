package <%= appPackage %>.network

import android.content.Context
import com.google.gson.Gson
import <%= appPackage %>.BuildConfig
import <%= appPackage %>.di.ForApplication
import <%= appPackage %>.model.OAuth
import <%= appPackage %>.service.LogoutWorker
import <%= appPackage %>.storage.Storage
import okhttp3.*
<% if (jodatime == true) { %> import org.joda.time.DateTime <% } %>
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OAuthInterceptor
@Inject
constructor(private val storage: Storage, @ForApplication private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val builder = request.newBuilder()

        val oauth: OAuth? = storage.get(OAuth.CLASS_NAME, OAuth::class.java)
        if (oauth != null) {
            builder.header("Authorization", "Bearer " + oauth.accessToken)
        }

        val token = oauth?.accessToken

        builder.addHeader("Content-Type", "application/json")

        request = builder.build()
        val response = chain.proceed(request)

        if (response.code() == 401) {
            val currentToken: String? = oauth?.accessToken

            if (oauth != null && currentToken != null && currentToken == token) {
                val code = refreshToken(currentToken, oauth.refreshToken)
                if ((code / 100) != 2) {
                    if (code == 401 || code == 403) {
                        LogoutWorker.logoutAndStopDownloadsDueToInvalidToken(context, storage)
                    }
                    return response
                }
            }

            if (oauth != null) {
                builder.header("Authorization", "Bearer " + oauth.accessToken)
                request = builder.build()
                return chain.proceed(request)
            }
        }

        return response
    }

    private fun refreshToken(accessToken: String?, refreshToken: String?): Int {
        val client: OkHttpClient = OkHttpClient()
        val url = BuildConfig.API_ENDPOINT_INTERNAL + "/oauth/token?refresh_token=$refreshToken&client_id=${BuildConfig.CLIENT_ID}&client_secret=${BuildConfig.CLIENT_SECRET}&grant_type=refresh_token"
        val request = Request.Builder().post(RequestBody.create(MediaType.parse("application/json"), ByteArray(0))).addHeader("Content-Type", "application/json").addHeader("Authorization", "Bearer " + accessToken).url(url).build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val token = Gson().fromJson(response.body()?.string(), OAuth::class.java)

            <% if (jodatime == true) { %>
              token.date = DateTime.now().plusSeconds(token.expiresIn)
            <% } else { %>
              val calendar = Calendar.getInstance()
              calendar.add(Calendar.SECOND, token.expiresIn)

              token.date = calendar
            <% } %>
            storage.set(OAuth.CLASS_NAME, token, OAuth::class.java)
        }
        response.body()?.close()

        return response.code()
    }

}

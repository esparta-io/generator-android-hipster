package <%= appPackage %>.network;

import java.util.concurrent.atomic.AtomicReference

import okhttp3.HttpUrl
import retrofit2.BaseUrl

class ChangeableBaseUrl(baseUrl: String) : BaseUrl {

    private val baseUrl: AtomicReference<HttpUrl>

    init {
        this.baseUrl = AtomicReference(HttpUrl.parse(baseUrl))
    }

    fun setBaseUrl(baseUrl: String) {
        this.baseUrl.set(HttpUrl.parse(baseUrl))
    }

    override fun url(): HttpUrl {
        return baseUrl.get()
    }

}

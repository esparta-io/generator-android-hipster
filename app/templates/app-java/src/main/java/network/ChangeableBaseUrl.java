package <%= appPackage %>.environment;

import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicReference;

import okhttp3.HttpUrl;
import retrofit2.BaseUrl;

public class ChangeableBaseUrl implements BaseUrl {

    @NonNull
    private final AtomicReference<HttpUrl> baseUrl;

    public ChangeableBaseUrl(@NonNull String baseUrl) {
        this.baseUrl = new AtomicReference<>(HttpUrl.parse(baseUrl));
    }

    public void setBaseUrl(@NonNull String baseUrl) {
        this.baseUrl.set(HttpUrl.parse(baseUrl));
    }

    @Override @NonNull
    public HttpUrl url() {
        return baseUrl.get();
    }

}

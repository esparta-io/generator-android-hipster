package <%= appPackage %>.domain.repository.<%= repositoryPackageName %>

import javax.inject.Inject

import retrofit2.adapter.rxjava2.Result
import retrofit2.Retrofit
import retrofit2.http.GET

import io.reactivex.Observable

class <%= repositoryName %>RemoteRepository <% if (interface == false) { %>@Inject<% } %> constructor(retrofit: Retrofit) {

    val service: <%= repositoryName %>Service by lazy { retrofit.create(<%= repositoryName %>Service::class.java) };

    interface <%= repositoryName %>Service {
        @GET("api/foo")
        fun foo(): Observable<Result<Void>>
    }
}

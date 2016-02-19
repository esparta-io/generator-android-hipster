package <%= appPackage %>.domain.repository.<%= repositoryPackageName %>

import javax.inject.Inject

import retrofit2.Result
import retrofit2.Retrofit
import retrofit2.http.GET

import rx.Observable

public class <%= repositoryName %>RemoteRepository
<% if (interface == false) { %>@Inject<% } %>
constructor(retrofit: Retrofit) {

    val <%= repositoryName %>Service service;

    init {
        this.service = retrofit.create(<%= repositoryName %>Service::class.java);
    }

    internal interface <%= repositoryName %>Service {
        @GET("api/foo")
        fun foo() : Observable<Result<Void>>
    }
}

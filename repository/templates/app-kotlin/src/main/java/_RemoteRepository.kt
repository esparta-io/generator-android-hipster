package <%= appPackage %>.domain.repository.<%= repositoryPackageName %>

import javax.inject.Inject

import retrofit2.adapter.rxjava2.Result
import retrofit2.Retrofit
import retrofit2.http.GET

import <%= appPackage %>.extensions.lazyUnsafe
import kotlinx.coroutines.Deferred

class <%= repositoryName %>RemoteRepository @Inject constructor(retrofit: Retrofit) {

    private val service: <%= repositoryName %>Service by lazyUnsafe { retrofit.create(<%= repositoryName %>Service::class.java) }

    fun foo(): Deferred<Void> = service.foo()

    interface <%= repositoryName %>Service {
        @GET("api/foo")
        fun foo(): Deferred<Void>
    }
}
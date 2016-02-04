package <%= appPackage %>.domain.repository.<%= repositoryPackageName %>;

import javax.inject.Inject;

import retrofit2.Result;
import retrofit2.Retrofit;
import retrofit2.http.GET;

import rx.Observable;

public class <%= repositoryName %>RemoteRepository {

    private final <%= repositoryName %>Service service;

    @Inject
    <%= repositoryName %>RemoteRepository(Retrofit retrofit) {
        this.service = retrofit.create(<%= repositoryName %>Service.class);
    }

    interface <%= repositoryName %>Service {
        @GET("api/foo")
        Observable<Result<Void>> foo();
    }
}

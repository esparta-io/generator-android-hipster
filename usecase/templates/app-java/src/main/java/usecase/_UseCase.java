package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import rx.Scheduler;
import <%= appPackage %>.domain.interactors.base.BaseUseCase;

import javax.inject.Inject;
import rx.Observable;

public class <%= useCaseName %>UseCase extends BaseUseCase  {

    @Inject
    public <%= useCaseName %>UseCase(Scheduler executor) {
        super(executor);
    }

    public Observable<Object> invoke() {
        return Observable.just(null);
    }

}

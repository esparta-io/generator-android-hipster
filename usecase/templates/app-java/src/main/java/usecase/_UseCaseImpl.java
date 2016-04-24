package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import rx.Scheduler;
import <%= appPackage %>.domain.interactors.base.BaseUseCase;
import rx.Observable;

public class <%= useCaseName %>UseCaseImpl extends BaseUseCase implements <%= useCaseName %>UseCase {

    public <%= useCaseName %>UseCaseImpl(Scheduler executor) {
        super(executor);
    }

    @Override
    public Observable<Object> invoke() {
        return Observable.just(null);
    }

}

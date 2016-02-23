package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseUseCase;
import rx.Observable;

public class <%= useCaseName %>UseCaseImpl extends BaseUseCase implements <%= useCaseName %>UseCase {

    public <%= useCaseName %>UseCaseImpl(ThreadExecutor executor) {
        super(executor);
    }

    @Override
    public Observable<Object> invoke() {
        return Observable.just(null);
    }

}

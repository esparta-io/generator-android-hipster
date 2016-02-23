package <

%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseUseCase;

import rx.Observable;

class <%= useCaseName %>UseCaseImpl(executor: ThreadExecutor) : BaseUseCase(executor), <%= useCaseName %>UseCase {

    override fun invoke(): Observable<Any> {
        return Observable.just(null);
    }

}

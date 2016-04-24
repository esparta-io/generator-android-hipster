package <

%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import <%= appPackage %>.domain.interactors.base.BaseUseCase;

import rx.Scheduler;
import rx.Observable;

class <%= useCaseName %>UseCaseImpl(executor: Scheduler) : BaseUseCase(executor), <%= useCaseName %>UseCase {

    override fun invoke(): Observable<Any> {
        return Observable.just(null);
    }

}

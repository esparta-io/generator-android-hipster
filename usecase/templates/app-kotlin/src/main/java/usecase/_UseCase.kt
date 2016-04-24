package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import rx.Scheduler;
import <%= appPackage %>.domain.interactors.base.BaseUseCase;

import javax.inject.Inject;
import rx.Observable;

class <%= useCaseName %>UseCase(executor: Scheduler) : BaseUseCase(executor)  {

    fun invoke() : Observable<Any> {
        // TODO
    }

}

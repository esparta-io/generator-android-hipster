package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseUseCase;

import javax.inject.Inject;
import rx.Observable;

class <%= useCaseName %>UseCase(executor: ThreadExecutor) : BaseUseCase(executor)  {

    fun invoke() : Observable<Any> {
        // TODO
    }

}

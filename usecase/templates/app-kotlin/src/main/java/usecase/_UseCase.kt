package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseUseCase;

import javax.inject.Inject;

class <%= useCaseName %>UseCase(executor: ThreadExecutor) : BaseUseCase(executor)  {

    fun invoke() {
        // TODO
    }

}

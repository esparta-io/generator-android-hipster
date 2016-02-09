package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseInteractor;

import javax.inject.Inject;

class <%= interactorName %>Interactor(executor: ThreadExecutor)  : BaseInteractor(executor)  {

    fun invoke() {
        // TODO
    }

}

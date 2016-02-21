package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseInteractor;

class <%= interactorName %>InteractorImpl (executor: ThreadExecutor) : BaseInteractor(executor), <%= interactorName %>Interactor {

    fun invoke() {
        // TODO
    }

}

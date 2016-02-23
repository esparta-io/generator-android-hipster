package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseInteractor;

import rx.Observable;

class <%= interactorName %>InteractorImpl (executor: ThreadExecutor) : BaseInteractor(executor), <%= interactorName %>Interactor {

    fun invoke(): Observable<Any> {
        return Observable.just(null)
    }

}

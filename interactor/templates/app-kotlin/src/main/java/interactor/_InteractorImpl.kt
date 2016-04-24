package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>

import rx.Scheduler
import <%= appPackage %>.domain.interactors.base.BaseInteractor

import rx.Observable

class <%= interactorName %>InteractorImpl (executor: Scheduler) : BaseInteractor(executor), <%= interactorName %>Interactor {

    fun invoke(): Observable<Any> {
        return Observable.just(null)
    }

}

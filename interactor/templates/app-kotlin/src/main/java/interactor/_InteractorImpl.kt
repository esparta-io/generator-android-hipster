package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>

import io.reactivex.Scheduler
import <%= appPackage %>.domain.interactors.base.BaseInteractor

import io.reactivex.Observable

class <%= interactorName %>InteractorImpl (executor: Scheduler) : BaseInteractor(executor), <%= interactorName %>Interactor {

    fun invoke(): Observable<Any> {
        return Observable.just(null)
    }

}

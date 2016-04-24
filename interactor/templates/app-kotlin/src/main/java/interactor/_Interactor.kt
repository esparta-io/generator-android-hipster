package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>

import rx.Scheduler
import <%= appPackage %>.domain.interactors.base.BaseInteractor

import javax.inject.Inject
import rx.Observable

class <%= interactorName %>Interactor(executor: Scheduler) : BaseInteractor(executor)  {

    fun invoke(): Observable<Any> {
        return Observable.just(null);
    }

}

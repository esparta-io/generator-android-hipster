package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>

import io.reactivex.Scheduler
import <%= appPackage %>.domain.interactors.base.BaseInteractor

import javax.inject.Inject
import io.reactivex.Observable

class <%= interactorName %>Interactor @Inject constructor(executor: Scheduler) : BaseInteractor(executor)  {

    fun invoke(): Observable<Any> {
        return Observable.just(Any())
    }

}

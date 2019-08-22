package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>

import io.reactivex.Scheduler
import <%= appPackage %>.domain.interactors.base.BaseInteractor
import <%= appPackage %>.domain.repository.<%= interactorPackageName %>.<%= interactorName %>Repository

import javax.inject.Inject

class <%= interactorName %>Interactor @Inject constructor(executor: Scheduler) : BaseInteractor(executor)  {

    @Inject
    lateinit var repository: <%= interactorName %>Repository
}

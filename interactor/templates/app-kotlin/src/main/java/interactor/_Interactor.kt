package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>

import <%= appPackage %>.domain.interactors.base.BaseCoroutineInteractor
import <%= appPackage %>.ui.base.BaseViewCoroutineScope
import <%= appPackage %>.domain.repository.<%= interactorPackageName %>.<%= interactorName %>LocalRepository
import <%= appPackage %>.domain.repository.<%= interactorPackageName %>.<%= interactorName %>RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher

import javax.inject.Inject

class <%= interactorName %>Interactor @Inject constructor(dispatcher: CoroutineDispatcher) : BaseCoroutineInteractor(dispatcher)  {

    @Inject
    lateinit var remoteRepository: <%= interactorName %>RemoteRepository

    @Inject
    lateinit var localRepository: <%= interactorName %>LocalRepository
}

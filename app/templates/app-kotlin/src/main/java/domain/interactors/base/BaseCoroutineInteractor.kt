package <%= appPackage %>.domain.interactors.base

import kotlinx.coroutines.CoroutineDispatcher

open class BaseCoroutineInteractor(val dispatcher: CoroutineDispatcher)
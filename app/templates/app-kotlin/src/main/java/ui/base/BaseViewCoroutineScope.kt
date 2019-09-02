package <%= appPackage %>.ui.base

import <%= appPackage %>.ui.base.ProgressPresenterView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

interface BaseViewCoroutineScope: CoroutineScope {

    var job: Job?

    override val coroutineContext: CoroutineContext
        get() {
            val j = job ?: throw IllegalStateException("job not initialized")
            return Dispatchers.Main + j + defaultCoroutineExceptionHandler()
        }

    fun createSupervisorJob() {
        job = SupervisorJob()
    }

    fun defaultCoroutineExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            Timber.e(exception, "Coroutine exception not handled")
            if (this is ProgressPresenterView) {
                (this as ProgressPresenterView).hideProgress()
            }
        }
    }
}
package <%= appPackage %>.ui.base

import <%= appPackage %>.di.HasComponent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import <%= appPackage %>.ui.base.BaseViewCoroutineScope
import kotlinx.coroutines.Job

abstract class BaseFragment<out P : BasePresenter<*>> : Fragment(), BaseViewCoroutineScope {

    override var job: Job? = null

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    @CallSuper
    override fun onCreate(bundle: Bundle? ) {
        super.onCreate(bundle)
        createSupervisorJob()
        inject()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
        getPresenter().destroy()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        job?.cancel()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        createSupervisorJob()
    }

    fun getBaseActivity() : BaseActivity<*> {
        return activity as BaseActivity<*>
    }

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    protected fun <C> getComponent(componentType: Class<C>): C? {
        return componentType.cast((activity as HasComponent<*>).getComponent())
    }

    protected abstract fun inject()

    protected abstract fun getLayoutResource(): Int

    protected abstract fun getPresenter(): P

}

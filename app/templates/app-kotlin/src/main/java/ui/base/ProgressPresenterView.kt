package <%= appPackage %>.ui.base

/**
 * Created by gmribas on 21/12/16.
 */
interface ProgressPresenterView : PresenterView{

    fun showProgress()

    fun hideProgress()
}
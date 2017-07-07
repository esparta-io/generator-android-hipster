package <%= appPackage %>.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ProgressBar
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by esparta on 30/08/16.
 */

 fun View.enableView() {
     enableDisableView(true)
 }

 fun View.disableView() {
     enableDisableView(false)
 }

fun View.enableDisableView(enabled: Boolean) {
    this.isEnabled = enabled
    this.isClickable = enabled
    this.alpha = if (enabled) 1f else .5f
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.fadeIn() {
    this.animate().alpha(1f).start()
}

fun View.fadeOut() {
    this.animate().alpha(0f).start()
}

fun Context.enableDisableViews(enabled: Boolean, vararg views: View) {
    views.forEach {
        it.enableDisableView(enabled)
    }
}

fun Context.setViewsInvisible(vararg views: View) {
    views.forEach(View::setInvisible)
}

fun Context.setViewsVisible(vararg views: View) {
    views.forEach(View::setVisible)
}

fun Context.setViewsGone(vararg views: View) {
    views.forEach(View::setGone)
}

/**
 * Define color filter no background
 */
fun EditText.setUnderlineColor(@ColorRes color: Int) {
    background.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
}

fun ProgressBar.setProgressColor(@ColorRes color: Int) {
    indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
}

fun AppCompatActivity.snackbar(view: View, @StringRes text: Int, length: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(view, text, length).show()
}

fun AppCompatActivity.snackbar(view: View, text: String, length: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(view, text, length).show()
}

fun AppCompatActivity.snackbarAndAction(view: View, @StringRes text: Int, @StringRes actionText: Int, actionListener: View.OnClickListener, length: Int = Snackbar.LENGTH_INDEFINITE){
    Snackbar.make(view, text, length)
            .setAction(actionText, actionListener)
            .show()
}

fun AppCompatActivity.changeStatusBarColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = color
    }
}

private class VarDelegate<T>(initializer: () -> T) : ReadWriteProperty<Any?, T> {

    private var initializer: (() -> T)? = initializer

    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (value == null) {
            value = initializer!!()
        }
        return value!!
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}

// http://stackoverflow.com/questions/34346966/kotlin-lazy-default-property/34347410#34347410
object DelegatesExt {
    fun <T> lazyVar(initializer: () -> T): ReadWriteProperty<Any?, T> = VarDelegate(initializer)
}

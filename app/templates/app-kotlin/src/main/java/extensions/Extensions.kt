package <%= appPackage %>.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar

/**
 * Created by esparta on 30/08/16.
 */

/**
 * Define a view como clicavel ou nao, define tambem alpha 0.5f ou 1f
 */
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
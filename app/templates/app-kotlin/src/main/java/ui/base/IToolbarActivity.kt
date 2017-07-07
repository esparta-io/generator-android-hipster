package <%= appPackage %>.ui.base

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import <%= appPackage %>.R

interface IToolbarActivity {

    fun configureToolbar(toolbar: Toolbar, activity: AppCompatActivity, color: Int? = null, homeEnable: Boolean) {
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.title = ""
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(homeEnable)
        activity.supportActionBar?.setDisplayShowHomeEnabled(homeEnable)
        color?.let{
            val upArrow: Drawable? = ContextCompat.getDrawable(activity, R.drawable.abc_ic_ab_back_material)
            upArrow?.let {
                upArrow.setColorFilter(ContextCompat.getColor(activity, color), PorterDuff.Mode.SRC_ATOP)
                activity.supportActionBar?.setHomeAsUpIndicator(upArrow)
            }
        }

    }

    fun handleHomePressed(activity: AppCompatActivity, item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity.onBackPressed()
                return true
            }
        }
        return false
    }

}

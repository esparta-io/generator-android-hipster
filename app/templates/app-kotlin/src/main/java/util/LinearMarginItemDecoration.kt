package <%= appPackage %>.util

import android.graphics.Rect
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class LinearMarginItemDecoration(private val margin: Int, private val orientation: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.right = margin
        } else {
            outRect.bottom = margin / 2
            outRect.top = margin / 2
        }
    }
}

package <%= appPackage %>.ui.base

import android.support.annotation.LayoutRes
import android.support.transition.TransitionManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Filter
import android.widget.Filterable
import java.util.*

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.ViewHolder<T>>(), Filterable {

    private var items: MutableList<T>
    protected var originalItems: List<T>

    private var mOnItemClickListener: AdapterView.OnItemClickListener? = null

    private var baseFilter: BaseFilter<T>? = null

    protected var parent : ViewGroup? = null

    init {
        items = ArrayList<T>()
        originalItems = ArrayList<T>()
    }

    protected fun setItems(items: MutableList<T>) {
        this.items = items
    }

    @LayoutRes
    protected abstract fun getLayoutForType(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val inflatedView = LayoutInflater.from(parent.context).inflate(getLayoutForType(viewType), parent, false)
        this.parent = parent
        return onCreateViewHolder(inflatedView, viewType)
    }

    protected abstract fun onCreateViewHolder(inflatedView: View, viewType: Int = 0): ViewHolder<T>

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bindItem(items[position], position)
    }

    fun getItem(position: Int): T? {
        return this.items[position]
    }

    override fun getFilter(): Filter {
        if (baseFilter == null){
            originalItems = ArrayList<T>(items)
            baseFilter = BaseFilter(this, originalItems)
        }
        return baseFilter as BaseFilter
    }

    protected fun getBaseFilter(): BaseFilter<T> {
        if (baseFilter == null){
            originalItems = ArrayList<T>(items)
            baseFilter = BaseFilter(this, originalItems)
        }
        return baseFilter as BaseFilter
    }

    /**
     * @param item
     * *
     * @param position
     */
    fun add(item: T?, position: Int) {
        if (item == null || position >= items.size || position == -1) {
            return
        }
        items.add(position, item)
        notifyItemInserted(position)
        notifyDataSetChanged()
    }

    /**
     * @param item
     */
    fun add(item: T?) {
        if (item == null) {
            return
        }
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    /**
     * @param item
     */
    fun remove(item: T?) {
        if (item == null) {
            return
        }
        val position = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    /**
     * @param items
     */
    fun addAll(items: List<T>?) {
        if (items == null) {
            return
        }
        val length = items.size
        val start = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(start, length)
    }

    /**
     * @param items
     */
    fun replace(items: List<T>?) {
        if (items == null) {
            this.items = ArrayList<T>()
        } else {
            this.items = ArrayList(items)
        }

        notifyDataSetChanged()
    }

    fun clear() {
        this.items = ArrayList<T>()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    private fun onItemHolderClick(itemHolder: ViewHolder<T>) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener?.onItemClick(null, itemHolder.itemView,
                    itemHolder.adapterPosition, itemHolder.itemId)
        }
    }

    abstract class ViewHolder<T>(root: View?, val adapter: BaseAdapter<T>) : RecyclerView.ViewHolder(root), View.OnClickListener {
        init {
            root?.setOnClickListener(this)
        }

        abstract fun bindItem(value: T, position: Int)

        override fun onClick(v: View) {
            adapter.onItemHolderClick(this)
        }
    }

    inner class BaseFilter<T>(val baseAdapter : BaseAdapter<T>, val originalList : List<T>) : Filter() {

        var filteredList : MutableList<T> = mutableListOf()

        var filterExpression: ((predicate: T, filterPattern: String) -> Boolean )? = null

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            filteredList.clear()
            val results = FilterResults()

            if(filterExpression == null) filterExpression = { predicate, filterPattern -> predicate.toString().contains(filterPattern) }

            if (constraint?.length == 0) {
                filteredList.addAll(originalList)
            } else {
                val filterPattern: String = constraint.toString().toLowerCase().trim()

                filteredList.addAll(
                        originalList.filter { filterExpression?.invoke(it, filterPattern) ?: false }
                )
            }
            results.values = filteredList
            results.count = filteredList.size
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            baseAdapter.clear()
            baseAdapter.addAll(results?.values as ArrayList<T>)
        }

    }

    interface OnItemClick<T> {
        fun itemClick(item: T, position: Int)
    }
}

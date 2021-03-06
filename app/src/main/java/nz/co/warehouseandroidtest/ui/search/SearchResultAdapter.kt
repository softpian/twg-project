package nz.co.warehouseandroidtest.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.models.SearchResultItem
import java.util.*

class SearchResultAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val searchResultItem: MutableList<SearchResultItem> = ArrayList()

    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    private var currentLoadState = 2

    val LOADING = 1
    val LOADING_COMPLETE = 2
    val LOADING_END = 3

    fun setSearchResultItem(searchResultItem: List<SearchResultItem>?) {
        searchResultItem?.let {
            this.searchResultItem.clear()
            this.searchResultItem.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position + 1 == itemCount) {
            TYPE_FOOTER
        } else {
            TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_layout, parent, false)
            SearchResultViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_refresh_footer, parent, false)
            FooterViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchResultViewHolder) {
            holder.bind(searchResultItem[position])
        } else if (holder is FooterViewHolder) {
            val footerViewHolder = holder
            when (currentLoadState) {
                LOADING -> {
                    footerViewHolder.pbLoading.visibility = View.VISIBLE
                    footerViewHolder.tvLoading.visibility = View.VISIBLE
                    footerViewHolder.llEnd.visibility = View.GONE
                }
                LOADING_COMPLETE -> {
                    footerViewHolder.pbLoading.visibility = View.INVISIBLE
                    footerViewHolder.tvLoading.visibility = View.INVISIBLE
                    footerViewHolder.llEnd.visibility = View.GONE
                }
                LOADING_END -> {
                    footerViewHolder.pbLoading.visibility = View.GONE
                    footerViewHolder.tvLoading.visibility = View.GONE
                    footerViewHolder.llEnd.visibility = View.VISIBLE
                }
                else -> {
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return searchResultItem.size + 1
    }

    fun setLoadState(loadState: Int) {
        currentLoadState = loadState
        notifyDataSetChanged()
    }
}
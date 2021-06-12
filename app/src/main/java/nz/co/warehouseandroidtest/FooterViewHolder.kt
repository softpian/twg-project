package nz.co.warehouseandroidtest

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @JvmField
    var pbLoading: ProgressBar
    @JvmField
    var tvLoading: TextView
    @JvmField
    var llEnd: LinearLayout

    init {
        pbLoading = itemView.findViewById<View>(R.id.pb_loading) as ProgressBar
        tvLoading = itemView.findViewById<View>(R.id.tv_loading) as TextView
        llEnd = itemView.findViewById<View>(R.id.ll_end) as LinearLayout
    }
}
package nz.co.warehouseandroidtest

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val pbLoading: ProgressBar by lazy { itemView.findViewById(R.id.pb_loading) }
    val tvLoading: TextView by lazy { itemView.findViewById(R.id.tv_loading) }
    val llEnd: LinearLayout by lazy { itemView.findViewById(R.id.ll_end) }
}
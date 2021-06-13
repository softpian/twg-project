package nz.co.warehouseandroidtest.ui.search

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nz.co.warehouseandroidtest.ui.detail.ProductDetailActivity
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.models.SearchResultItem

class SearchResultViewHolder(private val mItemView: View)
    : RecyclerView.ViewHolder(mItemView)
{
    private val mIvProduct: ImageView by lazy { mItemView.findViewById(R.id.product_imageView) }
    private val mTvProductName: TextView by lazy { mItemView.findViewById(R.id.name_textView) }
    private val mTvClass: TextView by lazy { mItemView.findViewById(R.id.class_textView) }
    private val mTvSubClass: TextView by lazy { mItemView.findViewById(R.id.subClass_textView) }
    private val mTvDept: TextView by lazy { mItemView.findViewById(R.id.dept_textView) }
    private val mTvSubDept: TextView by lazy { mItemView.findViewById(R.id.subDept_textView) }

    fun bind(item: SearchResultItem) {
        if (!TextUtils.isEmpty(item.description)) {
            mTvProductName.text = item.description
        }
        item.products?.get(0)?.let { productWithoutPrice ->
            Glide.with(mIvProduct.context)
                .load(productWithoutPrice.imageURL)
                .placeholder(R.mipmap.ic_pic_place_holder)
                .into(mIvProduct)

            mTvClass.text = productWithoutPrice.class0 ?: "n/a"
            mTvSubClass.text = productWithoutPrice.subClass ?: "n/a"
            mTvDept.text = productWithoutPrice.dept ?: "n/a"
            mTvSubDept.text = productWithoutPrice.subDept ?: "n/a"

            mItemView.setOnClickListener {
                Intent(mItemView.context, ProductDetailActivity::class.java).apply {
                    putExtra(ProductDetailActivity.FLAG_BAR_CODE, productWithoutPrice.barcode)
                    mItemView.context.startActivity(this)
                }
            }
        }
    }
}
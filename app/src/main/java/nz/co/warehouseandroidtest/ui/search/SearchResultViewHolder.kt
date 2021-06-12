package nz.co.warehouseandroidtest.ui.search

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nz.co.warehouseandroidtest.ui.detail.ProductDetailActivity
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.models.ProductWithoutPrice

class SearchResultViewHolder(private val mItemView: View) : RecyclerView.ViewHolder(
    mItemView
) {
    private val mIvProduct: ImageView by lazy { mItemView.findViewById(R.id.iv_product) }
    private val mTvProductName: TextView by lazy { mItemView.findViewById(R.id.tv_product_name) }

    fun bind(product: ProductWithoutPrice?) {
        if (product == null) return
        if (!TextUtils.isEmpty(product.description)) {
            mTvProductName.text = product.description
        }
        if (!TextUtils.isEmpty(product.imageURL)) {
            Glide.with(mIvProduct.context).load(product.imageURL).into(mIvProduct)
        }
        mItemView.setOnClickListener {
            val intent = Intent()
            intent.setClass(mItemView.context, ProductDetailActivity::class.java)
            intent.putExtra(ProductDetailActivity.FLAG_BAR_CODE, product.barcode)
            mItemView.context.startActivity(intent)
        }
    }

//    init {
//        mIvProduct = mItemView.findViewById(R.id.iv_product)
//        mTvProductName = mItemView.findViewById(R.id.tv_product_name)
//    }
}
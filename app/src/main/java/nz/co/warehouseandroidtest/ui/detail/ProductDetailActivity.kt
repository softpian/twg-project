package nz.co.warehouseandroidtest.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.utils.Constants
import nz.co.warehouseandroidtest.utils.NetworkResult
import nz.co.warehouseandroidtest.utils.PreferenceUtil.getUserId
import nz.co.warehouseandroidtest.viewmodels.ProductDetailActivityViewModel
import java.util.*

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    companion object {
        const val FLAG_INTENT_SOURCE_SCAN = 10001
        const val FLAG_INTENT_SOURCE_SEARCH = 10002
        const val FLAG_BAR_CODE = "barCode"
        const val FLAG_INTENT_SOURCE = "intentSource"
    }

    private lateinit var ivProduct: ImageView
    private lateinit var tvProduct: TextView
    private lateinit var ivClearance: ImageView
    private lateinit var tvPrice: TextView
    private lateinit var tvBarcode: TextView

    private lateinit var viewModel: ProductDetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        viewModel = ViewModelProvider(this)[ProductDetailActivityViewModel::class.java]

        ivProduct = findViewById(R.id.iv_product)
        tvProduct = findViewById(R.id.tv_product)
        ivClearance = findViewById(R.id.iv_clearance)
        tvPrice = findViewById(R.id.tv_price)
        tvBarcode = findViewById(R.id.tv_barcode)

        val barCode = intent.extras.getString(FLAG_BAR_CODE)

        val paramMap = HashMap<String, String>()
        paramMap["BarCode"] = barCode
        paramMap["MachineID"] = Constants.MACHINE_ID
        paramMap["UserID"] = getUserId(this) ?: ""
        paramMap["Branch"] = "${Constants.BRANCH_ID}"

        getProductDetail(paramMap)
    }

    private fun getProductDetail(paramMap: HashMap<String, String>) {
        viewModel.getProductDetail(paramMap)
        viewModel.productDetailResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { productDetail ->
                        productDetail.product?.let { product ->
                            Glide.with(this@ProductDetailActivity)
                                .load(product.imageURL)
                                .placeholder(R.mipmap.ic_pic_place_holder)
                                .into(
                                    ivProduct
                                )
                            tvProduct.text = product.itemDescription
                            val price = product.price?.price ?: "0"
                            tvPrice.text = "\$$price"
                            tvBarcode.text = product.barcode
                            val type = product.price?.type ?: ""
                            if (type == "CLR") {
                                ivClearance.visibility = View.VISIBLE
                            } else {
                                ivClearance.visibility = View.GONE
                            }
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "Get product detail failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {}
            }
        }
    }
}
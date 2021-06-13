package nz.co.warehouseandroidtest.ui.detail

import android.os.Bundle
import android.util.Log
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
    private lateinit var tvClass0: TextView
    private lateinit var tvClass: TextView
    private lateinit var tvSubClass: TextView
    private lateinit var tvDept: TextView
    private lateinit var tvSubDept: TextView
    private lateinit var tvItemDescription: TextView

    private lateinit var viewModel: ProductDetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        viewModel = ViewModelProvider(this)[ProductDetailActivityViewModel::class.java]

        ivProduct = findViewById(R.id.product_imageView)
        tvProduct = findViewById(R.id.description_textView)
        ivClearance = findViewById(R.id.clearance_imageView)
        tvPrice = findViewById(R.id.price_textView)
        tvBarcode = findViewById(R.id.barcode_value_textView)
        tvClass0 = findViewById(R.id.class0_textView)
        tvClass = findViewById(R.id.class_textView)
        tvSubClass = findViewById(R.id.subClass_textView)
        tvDept = findViewById(R.id.dept_textView)
        tvSubDept = findViewById(R.id.subDept_textView)
        tvItemDescription = findViewById(R.id.itemDescription_textView)


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
                                .into(ivProduct)
                            tvProduct.text = product.description ?: "n/a"
                            val price = product.price?.price ?: "0"
                            tvPrice.text = "\$$price"
                            tvBarcode.text = product.barcode
                            val type = product.price?.type ?: ""
                            if (type == "CLR") {
                                ivClearance.visibility = View.VISIBLE
                            } else {
                                ivClearance.visibility = View.GONE
                            }
                            tvClass0.text = product.class0 ?: "n/a"
                            tvClass.text = product.classProduct ?: "n/a"
                            tvSubClass.text = product.subClass ?: "n/a"
                            tvDept.text = product.dept ?: "n/a"
                            tvSubDept.text = product.subDept ?: "n/a"
                            if (product.itemDescription.isNullOrEmpty()) {
                                tvItemDescription.text = tvProduct.text
                            } else {
                                tvItemDescription.text = product.itemDescription
                            }
                            Log.d("HJM", "tvItemDescription.text : ${tvItemDescription.text }")
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
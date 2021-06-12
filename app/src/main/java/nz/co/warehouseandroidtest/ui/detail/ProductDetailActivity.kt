package nz.co.warehouseandroidtest.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.WarehouseTestApp
import nz.co.warehouseandroidtest.utils.PreferenceUtil.getUserId
import nz.co.warehouseandroidtest.models.ProductDetail
import nz.co.warehouseandroidtest.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

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

        (applicationContext as WarehouseTestApp).warehouseService.getProductDetail(paramMap)
            .enqueue(object : Callback<ProductDetail> {
                override fun onResponse(call: Call<ProductDetail>, response: Response<ProductDetail>) {
                    if (response.isSuccessful) {
                        val productDetail = response.body() as ProductDetail?
                        Glide.with(this@ProductDetailActivity)
                            .load(productDetail!!.product!!.imageURL).into(
                                ivProduct
                            )
                        tvProduct.text = productDetail.product!!.itemDescription
                        tvPrice.text = "$" + productDetail.product!!.price!!.price
                        tvBarcode.text = productDetail.product!!.barcode

                        if (productDetail.product!!.price!!.type == "CLR") {
                            ivClearance.visibility = View.VISIBLE
                        } else {
                            ivClearance.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(
                            this@ProductDetailActivity,
                            "Get product detail failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ProductDetail>, t: Throwable) {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "Get product detail failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
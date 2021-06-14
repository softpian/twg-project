package nz.co.warehouseandroidtest.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.models.Product
import nz.co.warehouseandroidtest.utils.Constants
import nz.co.warehouseandroidtest.utils.NetworkResult
import nz.co.warehouseandroidtest.utils.PreferenceUtil
import nz.co.warehouseandroidtest.viewmodels.ProductDetailActivityViewModel
import java.util.HashMap

private const val ARG_PARAM_BAR_CODE = "barCode"

@AndroidEntryPoint
class ProductImageFragment : Fragment() {

    private lateinit var ivProduct: ImageView
    private lateinit var tvProduct: TextView

    private val viewModel by viewModels<ProductDetailActivityViewModel>()
    private var mBarCode: String? = null

    companion object {
        @JvmStatic
        fun newInstance(barCode: String) =
            ProductImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_BAR_CODE, barCode)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mBarCode = it.getString(ARG_PARAM_BAR_CODE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_image, container, false)
        ivProduct = view.findViewById(R.id.product_imageView)
        tvProduct = view.findViewById(R.id.description_textView)

        val paramMap = HashMap<String, String>()
        paramMap["BarCode"] = mBarCode ?: ""
        paramMap["MachineID"] = Constants.MACHINE_ID
        paramMap["UserID"] = context?.let { PreferenceUtil.getUserId(it) } ?: ""
        paramMap["Branch"] = "${Constants.BRANCH_ID}"

        getProductDetail(paramMap)

        return view
    }

    private fun getProductDetail(paramMap: HashMap<String, String>) {
        viewModel.getProductDetail(paramMap)
        viewModel.productDetailResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { productDetail ->
                        productDetail.product?.let { product ->
                            bind(product)
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        context,
                        "Get product detail failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {}
            }
        }
    }

    private fun bind(product: Product) {
        activity?.let {
            Glide.with(this@ProductImageFragment)
                .load(product.imageURL)
                .placeholder(R.mipmap.ic_pic_place_holder)
                .into(ivProduct)
        }

        tvProduct.text = product.description ?: "n/a"
    }
}
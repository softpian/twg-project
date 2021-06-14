package nz.co.warehouseandroidtest.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
class ProductDetailFragment : Fragment() {

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

    private var mBarCode: String? = null

    companion object {
        @JvmStatic
        fun newInstance(barCode: String?): ProductDetailFragment {
            return ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_BAR_CODE, barCode)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductDetailActivityViewModel::class.java]
        arguments?.let {
            mBarCode = it.getString(ARG_PARAM_BAR_CODE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_detail, container, false)

        ivProduct = view.findViewById(R.id.product_imageView)
        tvProduct = view.findViewById(R.id.description_textView)
        ivClearance = view.findViewById(R.id.clearance_imageView)
        tvPrice = view.findViewById(R.id.price_textView)
        tvBarcode = view.findViewById(R.id.barcode_value_textView)
        tvClass0 = view.findViewById(R.id.class0_textView)
        tvClass = view.findViewById(R.id.class_textView)
        tvSubClass = view.findViewById(R.id.subClass_textView)
        tvDept = view.findViewById(R.id.dept_textView)
        tvSubDept = view.findViewById(R.id.subDept_textView)
        tvItemDescription = view.findViewById(R.id.itemDescription_textView)

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
            Glide.with(this@ProductDetailFragment)
                .load(product.imageURL)
                .placeholder(R.mipmap.ic_pic_place_holder)
                .into(ivProduct)
        }

        tvProduct.text = product.description ?: "n/a"
        val price = product.price?.price ?: "0"
        tvPrice.text = "\$$price"
        tvBarcode.text = product.barcode
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
        val type = product.price?.type ?: ""
        if (type == "CLR") {
            ivClearance.visibility = View.VISIBLE
        } else {
            ivClearance.visibility = View.GONE
        }
    }
}
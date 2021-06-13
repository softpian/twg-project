package nz.co.warehouseandroidtest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nz.co.warehouseandroidtest.data.Repository
import nz.co.warehouseandroidtest.models.ProductDetail
import nz.co.warehouseandroidtest.utils.NetworkResult
import nz.co.warehouseandroidtest.utils.ResponseUtil
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductDetailActivityViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val productDetailResponse: MutableLiveData<NetworkResult<ProductDetail>> = MutableLiveData()

    fun getProductDetail(paramMap: HashMap<String, String>)
        = viewModelScope.launch {
            getProductDetailSafeCall(paramMap)
        }

    suspend fun getProductDetailSafeCall(paramMap: HashMap<String, String>) {
        productDetailResponse.value = NetworkResult.Loading()
        val response = repository.remote.getProductDetail(paramMap)
        productDetailResponse.value = ResponseUtil.handleResponse(response)
    }
}
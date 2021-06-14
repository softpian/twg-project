package nz.co.warehouseandroidtest.data

import nz.co.warehouseandroidtest.models.ProductDetail
import nz.co.warehouseandroidtest.models.SearchResult
import nz.co.warehouseandroidtest.models.User
import retrofit2.Response
import java.util.*

interface RemoteDataSource {
    suspend fun getNewUserId(): Response<User>

    suspend fun getProductDetail(paramMap: HashMap<String, String>): Response<ProductDetail>

    suspend fun getSearchResult(paramMap: Map<String, String>): Response<SearchResult>
}
package nz.co.warehouseandroidtest.data.network

import nz.co.warehouseandroidtest.models.ProductDetail
import nz.co.warehouseandroidtest.models.SearchResult
import nz.co.warehouseandroidtest.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

interface WarehouseService {

    @GET("bolt/newuser.json")
    suspend fun getNewUserId(): Response<User>

    @GET("bolt/price.json")
    suspend fun getProductDetail(@QueryMap paramMap: HashMap<String, String>): Response<ProductDetail>

    @GET("bolt/search.json")
    suspend fun getSearchResult(@QueryMap paramMap: Map<String, String>): Response<SearchResult>
}
package nz.co.warehouseandroidtest

import nz.co.warehouseandroidtest.data.ProductDetail
import nz.co.warehouseandroidtest.data.SearchResult
import nz.co.warehouseandroidtest.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.HashMap

interface WarehouseService {
    @GET("bolt/newuser.json")
    fun getNewUserId(): Call<User?>?

    @GET("bolt/price.json")
    fun getProductDetail(@QueryMap paramMap: HashMap<String, String>): Call<ProductDetail>

    @GET("bolt/search.json")
    fun getSearchResult(@QueryMap paramMap: Map<String, String>): Call<SearchResult>
}
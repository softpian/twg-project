package nz.co.warehouseandroidtest.data

import nz.co.warehouseandroidtest.data.network.WarehouseService
import nz.co.warehouseandroidtest.models.ProductDetail
import nz.co.warehouseandroidtest.models.SearchResult
import nz.co.warehouseandroidtest.models.User
import retrofit2.Response
import java.util.HashMap
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val warehouseService: WarehouseService
) : RemoteDataSource {
    override suspend fun getNewUserId(): Response<User> {
        return warehouseService.getNewUserId()
    }

    override suspend fun getProductDetail(paramMap: HashMap<String, String>): Response<ProductDetail> {
        return warehouseService.getProductDetail(paramMap)
    }

    override suspend fun getSearchResult(paramMap: Map<String, String>): Response<SearchResult> {
        return warehouseService.getSearchResult(paramMap)
    }
}
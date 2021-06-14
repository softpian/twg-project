package nz.co.warehouseandroidtest.data

import nz.co.warehouseandroidtest.models.*
import nz.co.warehouseandroidtest.utils.Constants
import retrofit2.Response
import java.util.HashMap

class FakeRemoteDataSource : RemoteDataSource {

    override suspend fun getNewUserId(): Response<User> {
        return Response.success(Constants.getUser())
    }

    override suspend fun getProductDetail(paramMap: HashMap<String, String>): Response<ProductDetail> {
        return Response.success(Constants.getProductDetail())
    }

    override suspend fun getSearchResult(paramMap: Map<String, String>): Response<SearchResult> {
        return Response.success(Constants.getSearchResult())
    }
}
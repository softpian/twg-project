package nz.co.warehouseandroidtest.utils

import retrofit2.Response

object ResponseUtil {
    fun <T> handleResponse(response: Response<T>): NetworkResult<T> {
        when {
            response.isSuccessful -> {
                val responseBody = response.body()
                responseBody?.let {
                    return NetworkResult.Success(it)
                } ?: return NetworkResult.Error("Request failed")
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }
}
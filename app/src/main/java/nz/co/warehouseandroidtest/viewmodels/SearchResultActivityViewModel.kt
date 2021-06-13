package nz.co.warehouseandroidtest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nz.co.warehouseandroidtest.data.Repository
import nz.co.warehouseandroidtest.models.SearchResult
import nz.co.warehouseandroidtest.utils.NetworkResult
import nz.co.warehouseandroidtest.utils.ResponseUtil
import javax.inject.Inject

@HiltViewModel
class SearchResultActivityViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val searchResultResponse: MutableLiveData<NetworkResult<SearchResult>> = MutableLiveData()

    fun getSearchResult(paramMap: Map<String, String>)
        = viewModelScope.launch {
            getSearchResultSafeCall(paramMap)
        }

    suspend fun getSearchResultSafeCall(paramMap: Map<String, String>) {
        searchResultResponse.value = NetworkResult.Loading()
        val response = repository.remote.getSearchResult(paramMap)
        searchResultResponse.value = ResponseUtil.handleResponse(response)
    }
}
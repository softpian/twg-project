package nz.co.warehouseandroidtest.viewmodels

import androidx.lifecycle.LiveData
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

    private val _searchResultResponse: MutableLiveData<NetworkResult<SearchResult>> = MutableLiveData()
    val searchResultResponse: LiveData<NetworkResult<SearchResult>>
        get() = _searchResultResponse

    fun getSearchResult(paramMap: Map<String, String>)
        = viewModelScope.launch {
            getSearchResultSafeCall(paramMap)
        }

    suspend fun getSearchResultSafeCall(paramMap: Map<String, String>) {
        _searchResultResponse.value = NetworkResult.Loading()
        val response = repository.remote.getSearchResult(paramMap)
        _searchResultResponse.value = ResponseUtil.handleResponse(response)
    }
}
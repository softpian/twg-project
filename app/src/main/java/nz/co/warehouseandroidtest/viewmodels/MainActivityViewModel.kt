package nz.co.warehouseandroidtest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nz.co.warehouseandroidtest.data.Repository
import nz.co.warehouseandroidtest.models.User
import nz.co.warehouseandroidtest.utils.NetworkResult
import nz.co.warehouseandroidtest.utils.ResponseUtil
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val userResponse: MutableLiveData<NetworkResult<User>> = MutableLiveData()

    fun getNewUserId()
        = viewModelScope.launch {
            getNewUserIdSafeCall()
        }

    suspend fun getNewUserIdSafeCall() {
        userResponse.value = NetworkResult.Loading()
        val response = repository.remote.getNewUserId()
        userResponse.value = ResponseUtil.handleResponse(response)
    }
}
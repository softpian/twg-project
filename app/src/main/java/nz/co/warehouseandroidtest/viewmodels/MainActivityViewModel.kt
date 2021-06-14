package nz.co.warehouseandroidtest.viewmodels

import androidx.lifecycle.LiveData
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

    private val _userResponse: MutableLiveData<NetworkResult<User>> = MutableLiveData()
    val userResponse: LiveData<NetworkResult<User>>
        get() = _userResponse

    fun getNewUserId()
        = viewModelScope.launch {
            getNewUserIdSafeCall()
        }

    suspend fun getNewUserIdSafeCall() {
        _userResponse.value = NetworkResult.Loading()
        val response = repository.remote.getNewUserId()
        _userResponse.value = ResponseUtil.handleResponse(response)
    }
}
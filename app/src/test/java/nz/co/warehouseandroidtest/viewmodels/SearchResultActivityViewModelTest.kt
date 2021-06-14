package nz.co.warehouseandroidtest.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import nz.co.warehouseandroidtest.data.FakeRemoteDataSource
import nz.co.warehouseandroidtest.data.Repository
import nz.co.warehouseandroidtest.utils.Constants
import nz.co.warehouseandroidtest.utils.MainCoroutineRule
import nz.co.warehouseandroidtest.utils.PreferenceUtil
import nz.co.warehouseandroidtest.utils.getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.HashMap

class SearchResultActivityViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var searchResultActivityViewModel: SearchResultActivityViewModel

    @Before
    fun setUp() {
        val fakeRemoteDataSource = FakeRemoteDataSource()
        val repository = Repository(fakeRemoteDataSource)
        searchResultActivityViewModel = SearchResultActivityViewModel(repository)
    }

    @Test
    fun getSearchResult_returnNetworkResult_withCorrectSearchResult() {
        val paramsMap = HashMap<String, String>()
        searchResultActivityViewModel.getSearchResult(paramsMap)
        val networkResult = searchResultActivityViewModel.searchResultResponse.getOrAwaitValue()
        assertThat(networkResult.data).isEqualTo(Constants.getSearchResult())
    }
}
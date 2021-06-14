package nz.co.warehouseandroidtest.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import nz.co.warehouseandroidtest.data.FakeRemoteDataSource
import nz.co.warehouseandroidtest.data.Repository
import nz.co.warehouseandroidtest.utils.Constants
import nz.co.warehouseandroidtest.utils.MainCoroutineRule
import nz.co.warehouseandroidtest.utils.getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainActivityViewModel: MainActivityViewModel

    @Before
    fun setUp() {
        val fakeRemoteDataSource = FakeRemoteDataSource()
        val repository = Repository(fakeRemoteDataSource)
        mainActivityViewModel = MainActivityViewModel(repository)
    }

    @Test
    fun getNewUserId_retrunNetworkResult_withCorrectUser() {
        mainActivityViewModel.getNewUserId()
        val networkResult = mainActivityViewModel.userResponse.getOrAwaitValue()
        assertThat(networkResult.data).isEqualTo(Constants.getUser())
    }
}
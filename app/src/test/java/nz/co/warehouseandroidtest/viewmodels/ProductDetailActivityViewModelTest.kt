package nz.co.warehouseandroidtest.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import nz.co.warehouseandroidtest.data.FakeRemoteDataSource
import nz.co.warehouseandroidtest.data.Repository
import nz.co.warehouseandroidtest.utils.Constants
import nz.co.warehouseandroidtest.utils.MainCoroutineRule
import nz.co.warehouseandroidtest.utils.getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class ProductDetailActivityViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var productDetailActivityViewModel: ProductDetailActivityViewModel

    @Before
    fun setUp() {
        val fakeRemoteDataSource = FakeRemoteDataSource()
        val repository = Repository(fakeRemoteDataSource)
        productDetailActivityViewModel = ProductDetailActivityViewModel(repository)
    }

    @Test
    fun getProductDetail_returnNetworkResult_withCorrectProductDetail() {
        val paramMap = HashMap<String, String>()
        productDetailActivityViewModel.getProductDetail(paramMap)
        val networkResult = productDetailActivityViewModel.productDetailResponse.getOrAwaitValue()
        assertThat(networkResult.data).isEqualTo(Constants.getProductDetail())
    }
}
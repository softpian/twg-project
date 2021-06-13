package nz.co.warehouseandroidtest.ui.search

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.models.ProductWithoutPrice
import nz.co.warehouseandroidtest.utils.Constants
import nz.co.warehouseandroidtest.utils.NetworkResult
import nz.co.warehouseandroidtest.utils.PreferenceUtil.getUserId
import nz.co.warehouseandroidtest.viewmodels.SearchResultActivityViewModel
import java.util.*

@AndroidEntryPoint
class SearchResultActivity : AppCompatActivity() {

    companion object {
        var FLAG_KEY_WORD = "keyWord"
    }

    private var mKeyWord: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val searchResultAdapter: SearchResultAdapter by lazy { SearchResultAdapter() }

    private val data: MutableList<ProductWithoutPrice> = ArrayList()
    private var totalItemNum: String? = null
    private var startIndex = 0

    private lateinit var viewModel: SearchResultActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        viewModel = ViewModelProvider(this)[SearchResultActivityViewModel::class.java]

        swipeRefreshLayout = findViewById(R.id.refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            data.clear()
            loadData(0, 20)
            startIndex = 0
            swipeRefreshLayout.postDelayed({
                if (swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.isRefreshing = false
                }
            }, 1000)
        }
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(object : DividerItemDecoration(this, VERTICAL) {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top =
                    this@SearchResultActivity.resources.getDimensionPixelOffset(R.dimen.recyclerview_out_rec_top)
            }
        })
        searchResultAdapter.setData(data)
        recyclerView.adapter = searchResultAdapter
        recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                searchResultAdapter.setLoadState(searchResultAdapter.LOADING)
                if (data.size < totalItemNum!!.toInt()) {
                    loadData(startIndex, 20)
                } else {
                    searchResultAdapter.setLoadState(searchResultAdapter.LOADING_END)
                }
            }
        })
        mKeyWord = intent.extras?.getString(FLAG_KEY_WORD)
        loadData(startIndex, 20)
    }

    private fun loadData(startIndex: Int, itemsPerPage: Int) {

        val paramsMap = HashMap<String, String>()
        paramsMap["Search"] = mKeyWord ?: ""
        paramsMap["MachineID"] = Constants.MACHINE_ID
        paramsMap["UserID"] = getUserId(this) ?: ""
        paramsMap["Branch"] = Constants.BRANCH_ID.toString()
        paramsMap["Start"] = startIndex.toString()
        paramsMap["Limit"] = itemsPerPage.toString()

        getSearchResult(paramsMap)
    }

    private fun getSearchResult(paramMap: Map<String, String>) {
        viewModel.getSearchResult(paramMap)
        viewModel.searchResultResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { searchResult ->
                        val ifFound = searchResult.found
                        if (ifFound == "Y") {
                            totalItemNum = searchResult.hitCount
                            this@SearchResultActivity.startIndex += 20
                            searchResult.results?.let { results ->
                                for (item in results) {
                                    item.products?.let { products ->
                                        data.add(products[0])
                                    }
                                }
                            }
                            searchResultAdapter.setData(data)
                            searchResultAdapter.setLoadState(searchResultAdapter.LOADING_COMPLETE)
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        this@SearchResultActivity,
                        "Search failed!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is NetworkResult.Loading -> {}
            }
        }
    }
}
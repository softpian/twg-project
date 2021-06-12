package nz.co.warehouseandroidtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

class SearchActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchView = findViewById<View>(R.id.search_view) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()) {
                        Intent(this@SearchActivity, SearchResultActivity::class.java).run {
                            putExtra(SearchResultActivity.FLAG_KEY_WORD, query)
                            startActivity(this)
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }
}
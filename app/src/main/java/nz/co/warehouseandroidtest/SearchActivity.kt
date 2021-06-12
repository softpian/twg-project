package nz.co.warehouseandroidtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

class SearchActivity : AppCompatActivity() {
    private var searchView: SearchView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        searchView = findViewById<View>(R.id.search_view) as SearchView
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (null != query && query.length > 0) {
                    val intent = Intent()
                    intent.setClass(this@SearchActivity, SearchResultActivity::class.java)
                    intent.putExtra(SearchResultActivity.FLAG_KEY_WORD, query)
                    startActivity(intent)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }
}
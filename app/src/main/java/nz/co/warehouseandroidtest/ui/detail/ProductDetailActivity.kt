package nz.co.warehouseandroidtest.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import nz.co.warehouseandroidtest.R

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    companion object {
        const val FLAG_INTENT_SOURCE_SCAN = 10001
        const val FLAG_INTENT_SOURCE_SEARCH = 10002
        const val FLAG_BAR_CODE = "barCode"
        const val FLAG_INTENT_SOURCE = "intentSource"
    }

    private val tabLayout: TabLayout by lazy { findViewById(R.id.tabLayout) }
    private val viewPager2: ViewPager2 by lazy { findViewById(R.id.viewPager2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val barCode = intent.extras.getString(FLAG_BAR_CODE)

        val fragments = arrayListOf(
            ProductDetailFragment.newInstance(barCode),
            ProductImageFragment.newInstance(barCode),
            MoreInfoFragment()
        )

        val titles = arrayListOf(
            "Details",
            "Image",
            "More info"
        )

        ViewPagerAdapter(
            fragments,
            this
        ).let {
            viewPager2.adapter = it
        }

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}
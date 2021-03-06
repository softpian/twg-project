package nz.co.warehouseandroidtest.ui.scanning

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback
import nz.co.warehouseandroidtest.ui.detail.ProductDetailActivity
import nz.co.warehouseandroidtest.R

class BarScanActivity : AppCompatActivity() {

    private val captureFragment = CaptureFragment()
    private var isOpen = false
    private lateinit var ivFlashToggle: ImageView
    public override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_bar_scan)

        ivFlashToggle = findViewById(R.id.iv_flashlight)
        ivFlashToggle.setImageDrawable(getDrawable(R.mipmap.ic_flash_off_black_24dp))
        ivFlashToggle.setOnClickListener { toggleFlashlight() }

        CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_zxing_scan)

        captureFragment.analyzeCallback = analyzeCallback

        supportFragmentManager.beginTransaction().replace(R.id.fl_zxing_container, captureFragment)
            .commit()
    }

    private val analyzeCallback: AnalyzeCallback = object : AnalyzeCallback {
        override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
            Intent(this@BarScanActivity, ProductDetailActivity::class.java).run {
                putExtra(
                    ProductDetailActivity.FLAG_INTENT_SOURCE,
                    ProductDetailActivity.FLAG_INTENT_SOURCE_SCAN
                )
                putExtra(ProductDetailActivity.FLAG_BAR_CODE, result)
                startActivity(this)
            }
        }

        override fun onAnalyzeFailed() {
            Toast.makeText(
                this@BarScanActivity,
                "Oops, bar code analysis failed!",
                Toast.LENGTH_SHORT
            )
            finish()
        }
    }

    fun toggleFlashlight() {
        isOpen = if (!isOpen) {
            CodeUtils.isLightEnable(true)
            ivFlashToggle.setImageDrawable(getDrawable(R.mipmap.ic_flash_on_black_24dp))
            true
        } else {
            CodeUtils.isLightEnable(false)
            ivFlashToggle.setImageDrawable(getDrawable(R.mipmap.ic_flash_off_black_24dp))
            false
        }
    }
}
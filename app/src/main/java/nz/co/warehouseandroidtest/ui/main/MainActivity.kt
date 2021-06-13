package nz.co.warehouseandroidtest.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import nz.co.warehouseandroidtest.*
import nz.co.warehouseandroidtest.ui.scanning.BarScanActivity
import nz.co.warehouseandroidtest.ui.search.SearchActivity
import nz.co.warehouseandroidtest.utils.NetworkResult
import nz.co.warehouseandroidtest.utils.PreferenceUtil
import nz.co.warehouseandroidtest.viewmodels.MainActivityViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val permissions = arrayOf(Manifest.permission.CAMERA)

    private val checker = PermissionChecker(this)

    private val REQUEST_PERMISSION_CODE = 0

    private lateinit var tvScan: TextView
    private lateinit var tvSearch: TextView

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        tvScan = findViewById(R.id.tv_scan_barcode)
        tvSearch = findViewById(R.id.tv_search)

        tvScan.setOnClickListener {
            Intent(this@MainActivity, BarScanActivity::class.java).let {
                startActivity(it)
            }
        }

        tvSearch.setOnClickListener {
            Intent(this@MainActivity, SearchActivity::class.java).let {
                startActivity(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (checker.ifLackPermissions(permissions)) {
            PermissionActivity.startActivityForResult(this, REQUEST_PERMISSION_CODE, permissions)
        }

        getNewUserId()
    }

    private fun getNewUserId() {
        if (PreferenceUtil.getUserId(this) == null) {
            viewModel.getNewUserId()
            viewModel.userResponse.observe(this) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.let { user ->
                            user.userID?.let { userId ->
                                PreferenceUtil.putUserId(this@MainActivity, userId)
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Get User failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is NetworkResult.Loading -> {}
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK != resultCode) {
            return
        }
        if (requestCode == REQUEST_PERMISSION_CODE &&
            resultCode == PermissionActivity.PERMISSION_DENIED
        ) {
            finish()
        }
    }
}
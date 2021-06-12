package nz.co.warehouseandroidtest.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import nz.co.warehouseandroidtest.*
import nz.co.warehouseandroidtest.utils.PreferenceUtil
import nz.co.warehouseandroidtest.models.User
import nz.co.warehouseandroidtest.ui.scanning.BarScanActivity
import nz.co.warehouseandroidtest.ui.search.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val permissions = arrayOf(Manifest.permission.CAMERA)

    private val checker = PermissionChecker(this)

    private val REQUEST_PERMISSION_CODE = 0

    private lateinit var tvScan: TextView
    private lateinit var tvSearch: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        if (PreferenceUtil.getUserId(this) == null) {
            (applicationContext as WarehouseTestApp).warehouseService.getNewUserId()?.enqueue(
                object : Callback<User?> {
                override fun onResponse(call: Call<User?>, response: Response<User?>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        user?.userID?.let {
                            PreferenceUtil.putUserId(this@MainActivity, it)
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Get User failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Get User failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
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
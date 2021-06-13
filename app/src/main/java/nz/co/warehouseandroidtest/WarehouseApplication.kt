package nz.co.warehouseandroidtest

import android.app.Application
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WarehouseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ZXingLibrary.initDisplayOpinion(this)
    }
}
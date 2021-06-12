package nz.co.warehouseandroidtest

import android.app.Application
import com.google.gson.GsonBuilder
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WarehouseTestApp : Application() {
    lateinit var warehouseService: WarehouseService

    override fun onCreate() {
        super.onCreate()
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Ocp-Apim-Subscription-Key", Constants.SUBSCRIPTION_KEY).build()
            chain.proceed(request)
        })
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.HTTP_URL_ENDPOINT)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        warehouseService = retrofit.create(WarehouseService::class.java)
        ZXingLibrary.initDisplayOpinion(this)
    }
}
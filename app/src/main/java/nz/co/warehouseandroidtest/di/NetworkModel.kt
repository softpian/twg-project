package nz.co.warehouseandroidtest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nz.co.warehouseandroidtest.BuildConfig
import nz.co.warehouseandroidtest.data.RemoteDataSource
import nz.co.warehouseandroidtest.data.RemoteDataSourceImpl
import nz.co.warehouseandroidtest.data.network.WarehouseService
import nz.co.warehouseandroidtest.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModel {

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor
        = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Ocp-Apim-Subscription-Key", Constants.SUBSCRIPTION_KEY).build()
            chain.proceed(request)
        }

    @Singleton
    @Provides
    fun provideNetworkInterceptor(): HttpLoggingInterceptor
        = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideHttpClient(
        interceptor: Interceptor,
        networkInterceptor: HttpLoggingInterceptor
    ): OkHttpClient
        = if (BuildConfig.IS_DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(networkInterceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory
        = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit
        = Retrofit.Builder()
            .baseUrl(Constants.HTTP_URL_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Singleton
    @Provides
    fun provideWarehouseService(retrofit: Retrofit): WarehouseService
        = retrofit.create(WarehouseService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(warehouseService: WarehouseService): RemoteDataSource
        = RemoteDataSourceImpl(warehouseService) as RemoteDataSource
}
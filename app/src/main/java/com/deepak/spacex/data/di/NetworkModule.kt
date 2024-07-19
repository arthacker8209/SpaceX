package com.deepak.spacex.data.di


import com.deepak.spacex.BuildConfig
import com.deepak.spacex.data.network.interceptor.NetworkReachabilityInterceptor
import com.deepak.spacex.data.network.interceptor.NetworkStateChecker
import com.deepak.spacex.data.network.service.SpaceXLaunchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(networkStateChecker: NetworkStateChecker): OkHttpClient {
        val timeOutInSeconds = 120
        val builder = OkHttpClient.Builder()
            .connectTimeout(timeOutInSeconds.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeOutInSeconds.toLong(), TimeUnit.SECONDS)
            .addInterceptor(NetworkReachabilityInterceptor(networkStateChecker))

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSpaceXLaunchApiService(retrofit: Retrofit) = retrofit.create(SpaceXLaunchApiService::class.java)
}
package com.example.jetpack.injection.module

import com.example.jetpack.configuration.Constant
import com.example.jetpack.network.interceptor.AccuWeatherInterceptor
import com.example.jetpack.network.service.AccuWeatherService
import com.example.jetpack.data.repository.SettingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * # Interceptors in OkHttp - https://www.linkedin.com/pulse/interceptors-okhttp-mohamad-abuzaid/
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /* Step 1 - Define Logging Interceptor which logs the request and response headers and bodies.*/
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    /* Step 2 - provide OkHttpClient with Interceptors */
    @Singleton
    @Provides
    fun provideOkHttpClient(settingRepository: SettingRepository): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AccuWeatherInterceptor(settingRepository = settingRepository))
            .build()
    }

    /* Step 3 - provide Retrofit Builder */
    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constant.ACCU_WEATHER_URL)
    }

    /* Step 4 - profile Accu Weather Service */
    @Singleton
    @Provides
    fun provideAccuWeatherService(retrofitBuilder: Retrofit.Builder): AccuWeatherService{
        return retrofitBuilder.build().create(AccuWeatherService::class.java)
    }
}
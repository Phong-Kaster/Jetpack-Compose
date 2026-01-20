package com.example.jetpack.injection.module

import com.example.jetpack.configuration.Constant
import com.example.jetpack.network.interceptor.AccuWeatherInterceptor
import com.example.jetpack.network.service.AccuWeatherService
import com.example.jetpack.data.repository.SettingRepository
import com.example.jetpack.network.service.DummyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * # Interceptors in OkHttp - https://www.linkedin.com/pulse/interceptors-okhttp-mohamad-abuzaid/
 * # [Advance Retrofit](https://medium.com/android-alchemy/advance-retrofit-handling-authentication-logging-errors-and-more-2b1c7b7cb26f)
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
    @WeatherClient
    fun providerAccuWeatherClient(settingRepository: SettingRepository): OkHttpClient {
       val accuWeatherInterceptor = AccuWeatherInterceptor(settingRepository = settingRepository)
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(accuWeatherInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideAccuWeatherService(
        @WeatherClient okHttpClient: OkHttpClient,
    ): AccuWeatherService {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constant.ACCU_WEATHER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AccuWeatherService::class.java)
    }



    @Singleton
    @Provides
    @DummyClient
    fun provideDummyClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providerDummyService(
        @DummyClient  okHttpClient: OkHttpClient,
    ): DummyService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(DummyService::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DummyClient
package com.example.jetpack.network.interceptor

import com.example.jetpack.configuration.Constant
import com.example.jetpack.repository.SettingRepository
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Interceptors in OkHttp - https://www.linkedin.com/pulse/interceptors-okhttp-mohamad-abuzaid/
 */
class AccuWeatherInterceptor
@Inject
constructor(
    private val settingRepository: SettingRepository
) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        // Chain.request represents the current request/response chain.
        var request: Request = chain.request()

        val url: HttpUrl = request.url
            .newBuilder()
            .addQueryParameter("apikey", Constant.ACCU_WEATHER_KEY)
            .addQueryParameter("details", "true")
            .addQueryParameter("language", settingRepository.getLanguage().code)
            .addQueryParameter("metric", "false")
            .build()

        request = request
            .newBuilder()
            .url(url)
            .build()


        val response: Response = chain.proceed(request)// pass the request/response on to the next interceptor or the server.
        return response
    }
}
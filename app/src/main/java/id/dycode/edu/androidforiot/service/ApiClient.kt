package id.dycode.edu.androidforiot.service

import com.google.gson.GsonBuilder
import id.dycode.edu.androidforiot.Application
import id.dycode.edu.androidforiot.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        fun getClient(): ApiInterface {

            val xSecret = "cmY1R2l2Y08xbmM3X0l3WlVQSVd4YnFtbEV3YTpPRXFPV2NsNEdoXzFlRDZLSTlnekxqVWFlOW9h"

            val preferenceHelper: PreferenceHelper = Application.preferenceHelper!!

            val okHttpLoggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG)
                okHttpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            else
                okHttpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

            val interceptor = Interceptor { chain ->

                var request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("X-Secret", xSecret)
                        .build()

                if (preferenceHelper.getAuthenticate().isNotEmpty()) {

                    if (preferenceHelper.getLoginResponse()!=null) {
                        request = chain.request().newBuilder()
                                .addHeader("Accept", "application/json")
                                .removeHeader("X-Secret")
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Authorization", "Bearer ${preferenceHelper.getAuthenticate()}")
                                .addHeader("X-IoT-JWT", "${preferenceHelper.getLoginResponse()?.xIoTJWT}")
                                .build()
                    }
                    else {
                        request = chain.request().newBuilder()
                                .addHeader("Accept", "application/json")
                                .removeHeader("X-Secret")
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Authorization", "Bearer "+preferenceHelper.getAuthenticate())
                                .build()
                    }
                }

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient().newBuilder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(okHttpLoggingInterceptor)
                    .addInterceptor(interceptor)
                    .build()

            val gson = GsonBuilder().create()

            //initialized retrofit
            val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BuildConfig.BASEURL)
                    .build()

            val service: ApiInterface = retrofit.create(
                    ApiInterface::class.java)

            return service
        }
    }


}

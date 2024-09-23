package com.example.minidelivery_customer.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080"
    @Volatile
    private var retrofit: Retrofit? = null

    // Retrofit 초기화 (토큰 없이 기본 초기화)
    fun initRetrofitWithoutToken() {
        val okHttpClient = OkHttpClient.Builder().build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 로그인 후 토큰을 사용하여 Retrofit 재초기화
    fun initRetrofitWithToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences("loginData", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("accessToken", null) // 토큰 가져오기

        if (token != null) {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(request)
                }
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    // Retrofit 인스턴스를 제공하는 메서드
    val instance: ApiService
        get() {
            return retrofit?.create(ApiService::class.java)
                ?: throw IllegalStateException("RetrofitClient is not initialized. Call initRetrofitWithoutToken() first.")
        }
}


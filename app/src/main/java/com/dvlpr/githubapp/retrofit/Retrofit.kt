package com.dvlpr.githubapp.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private const val BASE_URL = "https://api.github.com/"
    private val retrofitConnect = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    val apiRequest = retrofitConnect.create(RetrofitEndPoint::class.java)
}
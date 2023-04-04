package com.example.news.repository.interfecesRepository

import com.example.news.repository.ResponseDataNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterfaceNews {

    @GET("everything")
    fun getListNews(
        @Query("q") q : String,
        @Query("from") from : String,
        @Query("to") to : String,
        @Query("sortBy") sortBy : String,
        @Query("apiKey") apiKey : String,

    ): Call<ResponseDataNews>
}
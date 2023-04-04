package com.example.news.repository

import com.example.news.repository.interfecesRepository.RetrofitInterfaceNews
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {
    private val baseUrl = BASE_URL_API

    fun getRetrofit() : RetrofitInterfaceNews {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return retrofit.create(RetrofitInterfaceNews::class.java)
    }
}
package com.example.news.repository

import com.google.gson.annotations.SerializedName

data class ResponseDataNews (
    @field:SerializedName("articles") val listArticles: List<Articles>?,
)

data class Articles (
    @field:SerializedName("author") val author: String?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("urlToImage") val urlToImage: String?,
    @field:SerializedName("publishedAt") val publishedAt: String?,
    @field:SerializedName("url") val url: String?,
)











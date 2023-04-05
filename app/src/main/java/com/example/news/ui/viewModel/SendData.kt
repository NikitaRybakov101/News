package com.example.news.ui.viewModel

import com.example.news.repository.ResponseDataNews

sealed class SendData {

    data class SendParameterCountryNews(
        val country : String,
        val category : String
    ) : SendData()

    data class SendParameterNews(
        val query : String,
        val from : String,
        val to : String,
        val sortBy : String,
    ) : SendData()

}

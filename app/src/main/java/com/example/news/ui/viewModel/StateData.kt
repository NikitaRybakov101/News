package com.example.news.ui.viewModel

import com.example.news.repository.ResponseDataNews

sealed class StateData {
    data class Success(val listNews: ResponseDataNews) : StateData()
    data class Loading(val loading : String)     : StateData()
    data class Error  (val error   : Throwable)  : StateData()
}

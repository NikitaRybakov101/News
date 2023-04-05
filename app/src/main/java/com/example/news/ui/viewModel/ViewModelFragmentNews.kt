package com.example.news.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.repository.*
import com.example.news.repository.API_KEY
import com.example.news.repository.NOT_FOUND
import com.example.news.ui.viewModel.interfacesViewModel.InterfaceViewModelFragmentNews
import com.example.news.utils.showToast
import kotlinx.coroutines.*

class ViewModelFragmentNews(private val retrofit: RetrofitImpl) : ViewModel() , InterfaceViewModelFragmentNews {

    private val liveData = MutableLiveData<StateData>()
    override fun getLiveData() = liveData

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getListNews(sendData : SendData) {

        val retrofit = retrofit.getRetrofit()
        liveData.value = StateData.Loading(LOADING)

        scope.launch {
            runCatching {

                val response = when(sendData) {
                    is SendData.SendParameterNews -> {
                        retrofit.getListNews(sendData.query, sendData.from, sendData.to, sendData.sortBy, API_KEY).execute()
                    }
                    is SendData.SendParameterCountryNews -> {
                        retrofit.getListCountryNews(sendData.country, sendData.category, API_KEY).execute()
                    }
                }

                response
            }.onSuccess { response ->

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        liveData.value = StateData.Success(response.body()!!)
                    } else {
                        liveData.value = StateData.Error(Throwable(NOT_FOUND))
                    }
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    liveData.value = StateData.Error(Throwable(it.message))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
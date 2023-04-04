package com.example.news.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.repository.*
import com.example.news.repository.API_KEY
import com.example.news.repository.NOT_FOUND
import com.example.news.ui.viewModel.interfacesViewModel.InterfaceViewModelFragmentNews
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class ViewModelFragmentNews(private val retrofit: RetrofitImpl) : ViewModel() , InterfaceViewModelFragmentNews {

    private val liveData = MutableLiveData<StateData>()
    override fun getLiveData() = liveData

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getListNews(q : String) {

        val retrofit = retrofit.getRetrofit()
        scope.launch {
            runCatching {

                val response = retrofit.getListNews(q, "2023-04-03", "2023-04-03", "popularity", API_KEY).execute()

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
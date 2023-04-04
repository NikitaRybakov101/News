package com.example.news.ui.viewModel.interfacesViewModel

import androidx.lifecycle.MutableLiveData
import com.example.news.ui.viewModel.StateData

interface InterfaceViewModelFragmentNews {
    fun getLiveData() : MutableLiveData<StateData>
    fun getListNews(q : String)
}
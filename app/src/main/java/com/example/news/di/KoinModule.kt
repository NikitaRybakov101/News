package com.example.news.di

import com.example.news.repository.RetrofitImpl
import com.example.news.ui.viewModel.ViewModelFragmentNews
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {

    single(named(RETROFIT_IMPL)) {
        RetrofitImpl()
    }
}

val viewModelModule = module {
    viewModel(named(VIEW_MODEL)) { (retrofit: RetrofitImpl) ->
        ViewModelFragmentNews(retrofit)
    }
}



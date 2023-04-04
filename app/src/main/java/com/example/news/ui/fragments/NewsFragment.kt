package com.example.news.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.NewsFragmentBinding
import com.example.news.di.RETROFIT_IMPL
import com.example.news.di.VIEW_MODEL
import com.example.news.repository.Articles
import com.example.news.repository.RetrofitImpl
import com.example.news.ui.baseView.BaseViewBindingFragment
import com.example.news.ui.fragments.recycler.RecyclerNews
import com.example.news.ui.viewModel.StateData
import com.example.news.ui.viewModel.ViewModelFragmentNews
import com.example.news.utils.showToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class NewsFragment : BaseViewBindingFragment<NewsFragmentBinding>(NewsFragmentBinding::inflate) {

    private val retrofit : RetrofitImpl by inject(named(RETROFIT_IMPL))
    private val viewModel : ViewModelFragmentNews by viewModel(named(VIEW_MODEL))  {
        parametersOf(retrofit)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        listenerButtonChip()

        viewModel.getListNews("apple")
    }

    private fun initViewModel() { viewModel.getLiveData().observe(viewLifecycleOwner) { render(it) } }

    private fun render(stateData: StateData) {
        when(stateData) {
            is StateData.Loading -> {

            }
            is StateData.Success -> {

                stateData.listNews.listArticles?.let { listNews ->
                    initListNews(listNews)
                }
            }
            is StateData.Error -> {
                context?.showToast("ERROR NETWORK")
            }
        }
    }

    private fun initListNews(listNews : List<Articles>) = with(binding) {
        val recyclerView = RecyclerNews(listNews)
        recyclerNews.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        recyclerNews.adapter = recyclerView
    }

    private fun listenerButtonChip() = with(binding){
        chipSelectAppleNews.setOnClickListener {
            viewModel.getListNews("apple")
        }
        chipSelectTeslaNews.setOnClickListener {
            viewModel.getListNews("tesla")
        }

    }

    companion object {
        fun newInstance() = NewsFragment()
    }


}
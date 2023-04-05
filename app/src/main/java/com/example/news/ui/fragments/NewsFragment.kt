package com.example.news.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.databinding.NewsFragmentBinding
import com.example.news.di.RETROFIT_IMPL
import com.example.news.di.VIEW_MODEL
import com.example.news.repository.Articles
import com.example.news.repository.RetrofitImpl
import com.example.news.ui.baseView.BaseViewBindingFragment
import com.example.news.ui.fragments.recycler.CallbackRecycler
import com.example.news.ui.fragments.recycler.RecyclerNews
import com.example.news.ui.viewModel.RequestParameters
import com.example.news.ui.viewModel.SendData
import com.example.news.ui.viewModel.StateData
import com.example.news.ui.viewModel.ViewModelFragmentNews
import com.example.news.utils.showToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
class NewsFragment : BaseViewBindingFragment<NewsFragmentBinding>(NewsFragmentBinding::inflate) , CallbackRecycler{

    private var recyclerView = RecyclerNews(ArrayList(), this)
    private val sendDataFirs = RequestParameters.sendParameterNewsApple

    private val retrofit : RetrofitImpl by inject(named(RETROFIT_IMPL))
    private val viewModel : ViewModelFragmentNews by viewModel(named(VIEW_MODEL))  {
        parametersOf(retrofit)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        listenerButtonChip()
        initListNews()

        viewModel.getListNews(sendDataFirs)
    }

    private fun initViewModel() { viewModel.getLiveData().observe(viewLifecycleOwner) { render(it) } }

    private fun render(stateData: StateData) {
        when(stateData) {
            is StateData.Loading -> {
                setEmptyRecycler()
                binding.loadingProgressbar.start()

            }
            is StateData.Success -> {
                binding.loadingProgressbar.stop()
                stateData.listNews.listArticles?.let { listNews ->
                    setListNews(listNews)
                }
            }
            is StateData.Error -> {
                binding.loadingProgressbar.stop()
                context?.showToast(getString(R.string.error_network_mess))
            }
        }
    }

    private fun initListNews() = with(binding) {
        recyclerNews.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        recyclerNews.adapter = recyclerView
    }

    private fun setListNews(listNews : List<Articles>) {
        recyclerView.updateList(listNews as ArrayList<Articles>)
    }

    private fun setEmptyRecycler() {
        recyclerView.clearListNews()
    }

    private fun listenerButtonChip() = with(binding){
        chipSelectAppleNews.isChecked = true

        chipSelectAppleNews.setOnClickListener {
            chipGroup.clearCheck()
            chipSelectAppleNews.isChecked = true

            val sendData = RequestParameters.sendParameterNewsApple
            viewModel.getListNews(sendData)
        }
        chipSelectTeslaNews.setOnClickListener {
            chipGroup.clearCheck()
            chipSelectTeslaNews.isChecked = true

            val sendData = RequestParameters.sendParameterNewsTesla
            viewModel.getListNews(sendData)
        }
        chipSelectUsaNews.setOnClickListener {
            chipGroup.clearCheck()
            chipSelectUsaNews.isChecked = true

            val sendData = RequestParameters.sendParameterCountryUsaNews
            viewModel.getListNews(sendData)
        }
    }

    override fun readMoreUrl(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}
package com.example.news.ui.fragments.recycler

import com.example.news.repository.Articles

interface InterfaceRecycler {
    fun clearListNews()
    fun updateList(listNews: ArrayList<Articles>)
}
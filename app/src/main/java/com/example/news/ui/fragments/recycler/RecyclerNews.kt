package com.example.news.ui.fragments.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.databinding.ItemRecyclerNewsBinding
import com.example.news.repository.Articles
import com.example.news.ui.fragments.NewsFragment
import java.util.*

class RecyclerNews(private val listNews: ArrayList<Articles>, private val fragmentNews: NewsFragment) : RecyclerView.Adapter<RecyclerNews.ViewHolderItemsNews>() , InterfaceRecycler {

    companion object {
        private const val CARD_ELEVATION = 0f
    }

    private var _binding : ItemRecyclerNewsBinding? = null
    private val binding : ItemRecyclerNewsBinding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItemsNews {
        _binding = ItemRecyclerNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderItemsNews(binding.root,binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderItemsNews, position: Int) {
        val news = listNews[position]

       holder.itemClicked()

        news.title?.let { holder.headerNews.text = it}
        news.author?.let { author ->
            news.publishedAt?.let { date ->
                holder.headerAuthor.text = fragmentNews.getString(R.string.author_header) + author + fragmentNews.getString(R.string.publish_date) + date
            }
        }
        news.description?.let { holder.descriptionNews.text = it }

        news.urlToImage?.let { url ->
            Glide.with(holder.imageNews.context).load(url).placeholder(R.drawable.placeholdere_not_found).into(holder.imageNews)
        }

        holder.cardItemsNews.setCardBackgroundColor(getRandomColor())
        holder.cardItemsNews.elevation = CARD_ELEVATION
    }

    private fun getRandomColor(): Int {
        val random = Random()

        return Color.argb(
            255,
            random.nextInt(30) + 220,
            random.nextInt(30) + 220,
            random.nextInt(30) + 220
        )
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun clearListNews() {
        listNews.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolderItemsNews(view: View, binding: ItemRecyclerNewsBinding) : RecyclerView.ViewHolder(view), InterfaceViewHolderNotes {
        val headerNews = binding.headerNews
        val headerAuthor = binding.headerAuthor
        val descriptionNews = binding.descriptionNews
        val imageNews = binding.imageNews
        val cardItemsNews = binding.cardItemsNews
        private val chipReadMore = binding.chipReadMore

        override fun itemClicked() {
            chipReadMore.setOnClickListener {
                listNews[layoutPosition].url?.let { url ->
                    fragmentNews.readMoreUrl(url)
                }
            }
        }
    }

    interface InterfaceViewHolderNotes {
        fun itemClicked()
    }
}
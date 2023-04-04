package com.example.news.ui.fragments.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.databinding.ItemRecyclerNewsBinding
import com.example.news.repository.Articles
import com.example.news.ui.fragments.NewsFragment

class RecyclerNews(private val listNews: List<Articles>) : RecyclerView.Adapter<RecyclerNews.ViewHolderItemsNews>() {

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

  //   holder.itemRemoved()
  //   holder.itemClicked()

        news.title?.let { holder.headerNews.text = it}
        news.author?.let { author ->
            news.publishedAt?.let { date ->
                holder.headerAuthor.text = "Author: $author  |  publishedAt: $date"
            }
        }
        news.description?.let { holder.descriptionNews.text = it }

        news.urlToImage?.let { url ->
            Glide.with(holder.imageNews.context).load(url).centerCrop().into(holder.imageNews)
        }
        holder.cardItemsNews.elevation = CARD_ELEVATION
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    inner class ViewHolderItemsNews(view: View, binding: ItemRecyclerNewsBinding) : RecyclerView.ViewHolder(view) {
        val headerNews = binding.headerNews
        val headerAuthor = binding.headerAuthor
        val descriptionNews = binding.descriptionNews
        val imageNews = binding.imageNews
        val cardItemsNews = binding.cardItemsNews


   /*     private val deletedCard = binding.deletedCard
        private val materialCardViewYourCard = binding.materialCardViewYourCard

        override fun itemRemoved() {
            deletedCard.setOnClickListener {
                fragment.callbackRecyclerDeleteCard(listYourCardItem[layoutPosition])

                listYourCardItem.removeAt(layoutPosition)
                notifyItemRemoved(layoutPosition)
            }
        }

        override fun itemClicked() {
            materialCardViewYourCard.setOnClickListener {
                fragment.callbackRecycler(listYourCardItem[layoutPosition])
            }
        }*/
    }

    interface InterfaceViewHolderNotes {
        fun itemRemoved()
        fun itemClicked()
    }
}
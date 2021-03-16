package com.companyname.catalog.presentation.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.companyname.common.entities.BaseMovie

const val SKELETON_ITEMS_COUNT = 15

class MoviesAdapter(private val loadMoreListener: LoadMoreListener?,
                    private val clickListener: (BaseMovie) -> Unit,
                    private val itemsLeftToLoadMore: Int) : RecyclerView.Adapter<BindMovieViewHolder>() {
    private var moviesList: MutableList<BaseMovie> = mutableListOf()
    private var currentType: AdapterType = AdapterType.FULL
    private var showSkeleton = false

    override fun getItemCount() = if (!showSkeleton) moviesList.size else SKELETON_ITEMS_COUNT

    override fun getItemViewType(position: Int) = getViewType(showSkeleton, currentType)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindMovieViewHolder {
        return ViewHolderFactory().getViewHolder(ViewHolderType.values()[viewType], parent)
    }

    override fun onBindViewHolder(holder: BindMovieViewHolder, position: Int) {
        if (!showSkeleton) {
            holder.bind(moviesList[position])
            if (position > itemCount - itemsLeftToLoadMore)
                loadMoreListener?.loadMore()
            holder.itemView.setOnClickListener { clickListener.invoke(moviesList[position]) }
        }
    }

    fun showSkeleton(){
        showSkeleton = true
        notifyDataSetChanged()
    }

    fun setData(newMovies: List<BaseMovie>) {
        showSkeleton = false
        this.moviesList = newMovies.toMutableList()
        notifyDataSetChanged()
    }

    fun changeRepresentation(type: AdapterType) {
        currentType = type
        notifyDataSetChanged()
    }
}

abstract class BindMovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(movie: BaseMovie)
}

interface LoadMoreListener{
    fun loadMore()
}
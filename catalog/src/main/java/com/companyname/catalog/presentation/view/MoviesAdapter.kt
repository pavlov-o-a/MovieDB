package com.companyname.catalog.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.companyname.catalog.R
import com.companyname.common.Constants
import com.companyname.common.entities.BaseMovie
import kotlinx.android.synthetic.main.holder_movie.view.*
import kotlinx.android.synthetic.main.holder_movie_light.view.*
import kotlinx.android.synthetic.main.holder_movie_light.view.tvTitle

const val LIGHT_HOLDER = 0
const val FULL_HOLDER = 1
const val SKELETON_LIGHT_HOLDER = 2
const val SKELETON_HOLDER = 3
const val SKELETON_ITEMS_COUNT = 15

class MoviesAdapter(private val context: Context,
                    private val loadMoreListener: LoadMoreListener?,
                    private val clickListener: (BaseMovie)->Unit,
                    private val itemsLeftToLoadMore: Int): RecyclerView.Adapter<BindMovieViewHolder>() {
    private var moviesList: MutableList<BaseMovie> = mutableListOf()
    private var currentType: Int = LIGHT_HOLDER
    private var showSkeleton = false

    override fun getItemCount() = if (!showSkeleton) moviesList.size else SKELETON_ITEMS_COUNT

    override fun getItemViewType(position: Int) = if (!showSkeleton) currentType else (currentType + 2)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindMovieViewHolder {
        return when(viewType) {
            FULL_HOLDER -> MovieViewHolder(
                context,
                parent
            )
            LIGHT_HOLDER -> LightMovieViewHolder(
                context,
                parent
            )
            SKELETON_HOLDER -> SkeletonHolder(
                context,
                parent
            )
            else -> LightSkeletonHolder(
                context,
                parent
            )
        }
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

    fun setData(newMovies: List<BaseMovie>){
        showSkeleton = false
        this.moviesList = newMovies.toMutableList()
        notifyDataSetChanged()
    }

    fun switchRepresentation(type: Int){
        currentType = when(type){
            LIGHT_HOLDER -> FULL_HOLDER
            else -> LIGHT_HOLDER
        }
        notifyDataSetChanged()
    }
}

class LightMovieViewHolder(context: Context, root: ViewGroup): BindMovieViewHolder(
    LayoutInflater.from(context).inflate(R.layout.holder_movie_light, root, false )
){
    override fun bind(movie: BaseMovie){
        itemView.tvTitle.text = movie.title
        itemView.tvRatingLight.text = movie.rating.toString()
        itemView.tvYearLight.text = movie.year.split("-")[0]
    }
}

class MovieViewHolder(context: Context, root: ViewGroup): BindMovieViewHolder(
    LayoutInflater.from(context).inflate(R.layout.holder_movie, root, false )
){
    override fun bind(movie: BaseMovie){
        itemView.tvTitle.text = movie.title
        itemView.tvRating.text = movie.rating.toString()
        itemView.tvYear.text = movie.year
        val coverWidth = itemView.context.resources.getDimensionPixelSize(R.dimen.catalog_poster_width)
        val posterSize = Constants.POSTER_SIZES.first { it >= coverWidth}
        val imageUrl = Constants.IMAGES_URL +
                "w$posterSize/" +
                movie.posterPath
        Glide
            .with(itemView.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(itemView.ivPoster)
    }
}

class SkeletonHolder(context: Context, root: ViewGroup): BindMovieViewHolder(
    LayoutInflater.from(context).inflate(R.layout.holder_skeleton, root, false)
){
    override fun bind(movie: BaseMovie) {}
}

class LightSkeletonHolder(context: Context, root: ViewGroup): BindMovieViewHolder(
    LayoutInflater.from(context).inflate(R.layout.holder_skeleton_light, root, false)
){
    override fun bind(movie: BaseMovie) {}
}

abstract class BindMovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(movie: BaseMovie)
}

interface LoadMoreListener{
    fun loadMore()
}
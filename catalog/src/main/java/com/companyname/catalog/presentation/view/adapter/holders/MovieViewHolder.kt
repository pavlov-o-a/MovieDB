package com.companyname.catalog.presentation.view.adapter.holders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.companyname.catalog.R
import com.companyname.catalog.presentation.view.adapter.BindMovieViewHolder
import com.companyname.shared.Constants
import com.companyname.shared.entities.BaseMovie
import kotlinx.android.synthetic.main.holder_movie.view.*

class MovieViewHolder(context: Context, root: ViewGroup) : BindMovieViewHolder(
        LayoutInflater.from(context).inflate(R.layout.holder_movie, root, false)
) {
    override fun bind(movie: BaseMovie) {
        itemView.tvTitle.text = movie.title
        itemView.tvRating.text = movie.rating.toString()
        itemView.tvYear.text = movie.year
        val coverWidth = itemView.context.resources.getDimensionPixelSize(R.dimen.catalog_poster_width)
        val posterSize = Constants.POSTER_SIZES.first { it >= coverWidth }
        val imageUrl = Constants.IMAGES_URL +
                "w$posterSize/" +
                movie.posterPath
        Glide
                .with(itemView.context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(com.companyname.moviedb.R.drawable.image_placeholder)
                .into(itemView.ivPoster)
    }
}
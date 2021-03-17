package com.companyname.catalog.presentation.view.adapter.holders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.companyname.catalog.R
import com.companyname.catalog.databinding.HolderMovieBinding
import com.companyname.catalog.presentation.view.adapter.BindMovieViewHolder
import com.companyname.shared.Constants
import com.companyname.shared.entities.BaseMovie

class MovieViewHolder(context: Context, root: ViewGroup) : BindMovieViewHolder(
        LayoutInflater.from(context).inflate(R.layout.holder_movie, root, false)
) {
    val viewBind = HolderMovieBinding.bind(itemView)

    override fun bind(movie: BaseMovie) {
        viewBind.tvTitle.text = movie.title
        viewBind.tvRating.text = movie.rating.toString()
        viewBind.tvYear.text = movie.year
        val coverWidth =
            itemView.context.resources.getDimensionPixelSize(R.dimen.catalog_poster_width)
        val posterSize = Constants.POSTER_SIZES.first { it >= coverWidth }
        val imageUrl = Constants.IMAGES_URL +
                "w$posterSize/" +
                movie.posterPath
        Glide
            .with(itemView.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(com.companyname.moviedb.R.drawable.image_placeholder)
            .into(viewBind.ivPoster)
    }
}
package com.companyname.catalog.presentation.view.adapter.holders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.companyname.catalog.R
import com.companyname.catalog.databinding.HolderMovieLightBinding
import com.companyname.catalog.presentation.view.adapter.BindMovieViewHolder
import com.companyname.shared.entities.BaseMovie

class LightMovieViewHolder(context: Context, root: ViewGroup) : BindMovieViewHolder(
        LayoutInflater.from(context).inflate(R.layout.holder_movie_light, root, false)
) {
    val viewBind = HolderMovieLightBinding.bind(itemView)

    override fun bind(movie: BaseMovie) {
        viewBind.tvTitle.text = movie.title
        viewBind.tvRatingLight.text = movie.rating.toString()
        viewBind.tvYearLight.text = movie.year.split("-")[0]
    }
}
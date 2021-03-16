package com.companyname.catalog.presentation.view.adapter.holders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.companyname.catalog.R
import com.companyname.catalog.presentation.view.adapter.BindMovieViewHolder
import com.companyname.common.entities.BaseMovie
import kotlinx.android.synthetic.main.holder_movie_light.view.*

class LightMovieViewHolder(context: Context, root: ViewGroup) : BindMovieViewHolder(
        LayoutInflater.from(context).inflate(R.layout.holder_movie_light, root, false)
) {
    override fun bind(movie: BaseMovie) {
        itemView.tvTitle.text = movie.title
        itemView.tvRatingLight.text = movie.rating.toString()
        itemView.tvYearLight.text = movie.year.split("-")[0]
    }
}
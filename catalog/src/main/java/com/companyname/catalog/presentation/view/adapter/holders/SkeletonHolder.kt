package com.companyname.catalog.presentation.view.adapter.holders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.companyname.catalog.R
import com.companyname.catalog.presentation.view.adapter.BindMovieViewHolder
import com.companyname.common.entities.BaseMovie

class SkeletonHolder(context: Context, root: ViewGroup) : BindMovieViewHolder(
        LayoutInflater.from(context).inflate(R.layout.holder_skeleton, root, false)
) {
    override fun bind(movie: BaseMovie) {}
}
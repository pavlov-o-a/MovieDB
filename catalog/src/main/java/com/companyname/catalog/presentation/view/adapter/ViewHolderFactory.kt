package com.companyname.catalog.presentation.view.adapter

import android.view.ViewGroup
import com.companyname.catalog.presentation.view.adapter.holders.*

class ViewHolderFactory {
    fun getViewHolder(type: ViewHolderType, parent: ViewGroup): BindMovieViewHolder {
        return when (type) {
            ViewHolderType.FULL -> MovieViewHolder(parent.context, parent)
            ViewHolderType.LIGHT -> LightMovieViewHolder(parent.context, parent)
            ViewHolderType.SKELETON -> SkeletonHolder(parent.context, parent)
            ViewHolderType.LIGHT_SKELETON -> LightSkeletonHolder(parent.context, parent)
        }
    }
}
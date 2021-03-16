package com.companyname.catalog.presentation.view.adapter

enum class ViewHolderType {
    FULL,
    LIGHT,
    SKELETON,
    LIGHT_SKELETON,
}

fun getViewType(isSkeleton: Boolean, adapterType: AdapterType): Int {
    return when (adapterType) {
        AdapterType.LIGHT -> {
            if (isSkeleton) ViewHolderType.LIGHT_SKELETON.ordinal
            else ViewHolderType.LIGHT.ordinal
        }
        AdapterType.FULL -> {
            if (isSkeleton) ViewHolderType.SKELETON.ordinal
            else ViewHolderType.FULL.ordinal
        }
    }
}
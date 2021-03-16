package com.companyname.moviedb

import android.content.Context
import com.companyname.shared.entities.RepositoryError

fun RepositoryError.getNotification(context: Context): String {
    //todo add processing of different types of errors
    return context.getString(R.string.unknown_error)
}
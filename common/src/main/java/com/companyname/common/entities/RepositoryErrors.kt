package com.companyname.common.entities

import android.content.Context

enum class RepositoryErrors {
    NO_CONNECTION,
    TIMEOUT,
    UNKNOWN
}

fun RepositoryErrors.getNotification(context: Context): String{
    //todo add processing of different types of errors
    return context.getString(R.string.unknown_error)
}
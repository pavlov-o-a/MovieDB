package com.companyname.common.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.plus

fun AppMainScope(): CoroutineScope =
    MainScope() + CoroutineExceptionHandler { coroutineContext, throwable -> throwable.printStackTrace() }
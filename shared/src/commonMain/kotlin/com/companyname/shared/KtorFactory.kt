package com.companyname.shared

import com.companyname.shared.network.KtorWrapper

object KtorFactory {
    private val wrapper = KtorWrapper()

    fun getKtor() = wrapper
}
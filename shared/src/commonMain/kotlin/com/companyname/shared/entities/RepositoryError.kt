package com.companyname.shared.entities

enum class RepositoryError {
    NO_CONNECTION,
    TIMEOUT,
    UNKNOWN;

    private var msg: String? = ""
    fun message(msg: String?): RepositoryError {
        this.msg = msg
        return this
    }

    fun getMessage() = msg
}
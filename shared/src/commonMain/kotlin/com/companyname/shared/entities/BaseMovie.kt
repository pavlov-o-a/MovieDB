package com.companyname.shared.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
open class BaseMovie(
    val title: String,
    val rating: Float,
    val year: String,
    val posterPath: String,
    val id: Int
) {
    companion object {
        fun parse(string: String): BaseMovie {
            return Json.decodeFromString(string)
        }
    }
}

fun BaseMovie.serialize() = Json.encodeToString(this)
package com.companyname.common.entities

import java.io.Serializable

open class BaseMovie(val title: String,
                     val rating: Float,
                     val year: String,
                     val posterPath: String,
                     val id: Int): Serializable
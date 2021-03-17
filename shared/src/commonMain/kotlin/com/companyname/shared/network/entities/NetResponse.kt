package com.companyname.shared.network.entities

import kotlinx.serialization.Serializable

@Serializable
class NetResponse<T>(val data: T, val error: String? = "")
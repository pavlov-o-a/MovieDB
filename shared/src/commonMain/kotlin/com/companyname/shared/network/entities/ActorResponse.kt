package com.companyname.shared.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ActorResponse(
    val name: String?,
    val character: String?,
    @SerialName("profile_path") val photo: String?
)
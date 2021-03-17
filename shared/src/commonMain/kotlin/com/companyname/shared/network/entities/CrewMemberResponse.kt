package com.companyname.shared.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CrewMemberResponse(
    val name: String?,
    val job: String?,
    @SerialName("profile_path") val photo: String?
)
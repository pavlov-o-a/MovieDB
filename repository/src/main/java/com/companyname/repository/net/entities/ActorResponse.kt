package com.companyname.repository.net.entities

import com.google.gson.annotations.SerializedName

class ActorResponse(
    val name: String?,
    val character: String?,
    @SerializedName("profile_path") val photo: String?
)
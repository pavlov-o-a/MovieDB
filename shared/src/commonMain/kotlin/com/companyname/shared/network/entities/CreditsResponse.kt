package com.companyname.shared.network.entities

import kotlinx.serialization.Serializable

@Serializable
class CreditsResponse(
    val cast: List<ActorResponse>?,
    val crew: List<CrewMemberResponse>?
)
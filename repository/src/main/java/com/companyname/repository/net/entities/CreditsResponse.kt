package com.companyname.repository.net.entities

import com.companyname.common.entities.Actor
import com.companyname.common.entities.CrewMember

internal class CreditsResponse(val cast: List<ActorResponse>?,
                               val crew: List<CrewMemberResponse>?)
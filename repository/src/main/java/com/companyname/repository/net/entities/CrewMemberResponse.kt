package com.companyname.repository.net.entities

import com.google.gson.annotations.SerializedName

internal class CrewMemberResponse(val name: String?,
                                  val job: String?,
                                  @SerializedName("profile_path") val photo: String?)
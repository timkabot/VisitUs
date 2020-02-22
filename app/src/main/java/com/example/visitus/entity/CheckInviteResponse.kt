package com.example.visitus.entity

import com.google.gson.annotations.SerializedName

data class CheckInviteResponse(
    @SerializedName("can_select")
    val canSelect: Boolean
)
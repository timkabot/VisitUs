package com.example.visitus.entity

import com.google.gson.annotations.SerializedName

data class CreateInviteResponse(
    val success: Boolean,
    @SerializedName("invite") val invite: Invite
)
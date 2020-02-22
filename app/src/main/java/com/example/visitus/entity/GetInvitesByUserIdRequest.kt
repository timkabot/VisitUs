package com.example.visitus.entity

import com.google.gson.annotations.SerializedName

data class GetInvitesByUserIdRequest(
    @SerializedName("user_id")
    val userId: Int)
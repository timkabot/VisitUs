package com.example.visitus.entity

import com.google.gson.annotations.SerializedName

data class CreateVisitRequest(
    @SerializedName("invite_id")
    private val inviteId: Int)
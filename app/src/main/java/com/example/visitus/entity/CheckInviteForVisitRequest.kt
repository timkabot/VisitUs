package com.example.visitus.entity

import com.google.gson.annotations.SerializedName


data class CheckInviteForVisitRequest(
    @SerializedName("invite_id")
    private val inviteId: Int)
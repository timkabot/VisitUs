package com.example.visitus.entity

import java.io.Serializable

data class Visit( val invite: Invite,  val user: Profile
) : Serializable {
    val invitee : Profile
    get() = invite.user
}
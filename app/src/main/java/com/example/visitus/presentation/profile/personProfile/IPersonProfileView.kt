package com.example.visitus.presentation.profile.personProfile

import com.arellomobile.mvp.MvpView
import com.example.visitus.entity.Invite

interface IPersonProfileView : MvpView {
    fun updatePublications(invites: ArrayList<Invite>)
}
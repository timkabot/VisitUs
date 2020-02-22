package com.example.visitus.presentation.register.avatarInfo

import com.arellomobile.mvp.MvpView

interface IAvatarInfoView : MvpView {
    fun showProgress()
    fun showToast(message: String)

}
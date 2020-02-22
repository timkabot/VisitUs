package com.example.visitus.presentation.login

import com.arellomobile.mvp.MvpView

interface ILoginView : MvpView {
    fun onLogin()
    fun onRegister()
    fun onPrivacyPolicy()
    fun showBottomNavigationView()
    fun showToast(message : String)
}
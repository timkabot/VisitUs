package com.example.visitus.presentation.login.forgotPassword

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ForgotPasswordPresenter @Inject constructor(private val router: Router) :
    MvpPresenter<IForgotPasswordView>() {

    fun onBack(){
        router.exit()
    }
}
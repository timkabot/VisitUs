package com.example.visitus.presentation.register.createInvite

import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class CreateinvitePresenter @Inject constructor(
    private val router: Router
)  : MvpPresenter<ICreateInviteView>(){

    fun onRegisterFinished(){
            router.newRootScreen(Screens.LoginScreen)
    }
}
package com.example.visitus.presentation.register

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import com.example.visitus.entity.Profile
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class NewAccountPresenter : MvpPresenter<IRegisterView>() {

    @Inject
    lateinit var router: Router

    fun onPrivacyClicked() {
        router.navigateTo(Screens.PrivacyPolicyScreen)
    }

    fun onBack() {
        router.exit()
    }

    fun onNextClicked(account: Profile) {
        router.navigateTo(Screens.RegisterMainInfoScreen(account) )
    }

}
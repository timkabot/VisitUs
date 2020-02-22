package com.example.visitus.presentation.register.moreInfo

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MoreInfoPresenter @Inject constructor(
    private val router: Router
) : MvpPresenter<IMoreInfoView>() {
    fun onBack() {
        router.exit()
    }

    fun onNextClicked(account: com.example.visitus.entity.Profile) {
        router.navigateTo(Screens.RegisterAvatarInfoFragment(account))
    }
}
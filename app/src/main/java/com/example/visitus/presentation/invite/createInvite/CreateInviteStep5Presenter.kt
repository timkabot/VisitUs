package com.example.visitus.presentation.invite.createInvite

import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.model.interactors.InvitesInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class CreateInviteStep5Presenter @Inject constructor(private val visitsInteractor: InvitesInteractor,
                                                     private val router: Router
) : MvpPresenter<ICreateInviteView>() {

    fun onNextClick(){
    }

    fun onBackPressed() {
        router.exit()
    }
}
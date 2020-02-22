package com.example.visitus.presentation.invite.createInvite

import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import com.example.visitus.entity.Invite
import com.example.visitus.model.interactors.InvitesInteractor
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class CreateInviteStep4Presenter @Inject constructor(private val visitsInteractor: InvitesInteractor,
                                                     private val router: Router
) : MvpPresenter<ICreateInviteView>() {
    var compositeDisposable = CompositeDisposable()
    fun onNextClick(invite: Invite){
        router.navigateTo(Screens.CreateVisitImageUploadScreen(invite))
    }

    fun onBackPressed() {
        router.exit()
    }
}
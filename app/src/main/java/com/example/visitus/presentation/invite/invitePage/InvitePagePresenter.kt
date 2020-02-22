package com.example.visitus.presentation.invite.invitePage

import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import com.example.visitus.entity.Invite
import com.example.visitus.entity.Profile
import com.example.visitus.model.interactors.InvitesInteractor
import com.example.visitus.model.interactors.VisitsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class InvitePagePresenter @Inject constructor(private val visitsInteractor: VisitsInteractor,
                                              private val router: Router
) : MvpPresenter<IInvitePage>(){

    val disposables = CompositeDisposable()

    fun goToProfile(profile: Profile){
        router.navigateTo(Screens.PersonProfileScreen(profile))
    }

    fun onBackPressed() {
        router.exit()
    }

    fun createVisit(invite: Invite){
        visitsInteractor.createVisit(inviteId = invite.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    viewState.hideAcceptButton()
                },
                {
                    Timber.d("Error creating visit $it")

                }
            ).addTo(disposables)
    }

    fun checkInvite(inviteId: Int){
        visitsInteractor.checkInviteForVisit(inviteId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    if(it.canSelect) viewState.showAcceptButton()
                    else viewState.hideAcceptButton()
                },
                {
                    Timber.d("Error checking invite for visit $it")
                }
            )
            .addTo(disposables)
    }
}
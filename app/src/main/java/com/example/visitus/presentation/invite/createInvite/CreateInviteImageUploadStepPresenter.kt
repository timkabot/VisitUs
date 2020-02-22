package com.example.visitus.presentation.invite.createInvite

import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import com.example.visitus.entity.Invite
import com.example.visitus.model.interactors.InvitesInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class CreateInviteImageUploadStepPresenter @Inject constructor(private val visitsInteractor: InvitesInteractor,
                                                               private val router: Router
) : MvpPresenter<ICreateInviteView>() {
    var compositeDisposable = CompositeDisposable()

    fun onNextClick(invite: Invite){
        invite.location = invite.city + ", " + invite.country //TODO now location only in invite
        println("Location ${invite.location}")
        visitsInteractor.createInvite(invite)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                        success ->
                    println("Visit ID = ${success.invite.id}, and fiel path = ${invite.imageFilePath}")
                    Timber.d("Visit ID = ${success.invite.id}, and fiel path = ${invite.imageFilePath}")
                    visitsInteractor.uploadImage(success.invite.id, invite.imageFilePath)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                            {
                                success ->
                                router.navigateTo(Screens.CreateVisit5Screen)
                            },
                            {
                                error ->
                                Timber.d("Error uploading image ${error}")
                            }
                        )
                },
                {
                        error ->
                    Timber.d("Error when creating visit $error")
                }
            )
            .addTo(compositeDisposable)
    }

    fun onBackPressed() {
        router.exit()
    }
}
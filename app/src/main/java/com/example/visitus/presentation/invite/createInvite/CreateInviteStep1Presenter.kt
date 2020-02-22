package com.example.visitus.presentation.invite.createInvite

import com.arellomobile.mvp.InjectViewState
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

@InjectViewState
class CreateInviteStep1Presenter @Inject constructor(private val visitsInteractor: InvitesInteractor,
                                                     private val router: Router
) : MvpPresenter<ICreateInviteView>(){
    var compositeDisposable = CompositeDisposable()
    fun updateRecycler(){
        visitsInteractor.getInviteTypes()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { success ->
                    viewState.updateRecyclerView(success)
                },
                {
                    error ->
                    Timber.d("Error when getVisitTypes $error")
                }
            )
            .addTo(compositeDisposable)
    }
    fun onNextClick(invite: Invite){
        router.navigateTo(Screens.CreateVisit2Screen(invite))
    }

    fun onBackPressed(){
        router.exit()
    }

}
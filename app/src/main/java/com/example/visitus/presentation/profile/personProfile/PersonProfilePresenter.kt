package com.example.visitus.presentation.profile.personProfile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import com.example.visitus.model.data.storage.Prefs
import com.example.visitus.model.interactors.UserInteractor
import com.example.visitus.model.interactors.InvitesInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject
@InjectViewState
class PersonProfilePresenter @Inject constructor(
    private val router: Router,
    private val userInteractor: UserInteractor,
    private val invitesInteractor: InvitesInteractor
) : MvpPresenter<IPersonProfileView>() {

    val compositeDisposable = CompositeDisposable()

    fun onBack() {
        router.exit()
    }
    fun uploadAvatar(filePath: String)
    {
        userInteractor.uploadAvatar(filePath)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Timber.d("Avatar succesfully uploaded ${it}")
                },
                {
                    Timber.d("Avatar upload error ${it}")
                }
            ).addTo(compositeDisposable)
    }
    fun uploadBackground(filePath: String)
    {
        userInteractor.uploadBackground(filePath)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Timber.d("Background succesfully uploaded ${it}")
                },
                {
                    Timber.d("Background upload error ${it}")
                }
            ).addTo(compositeDisposable)
    }

    fun getUserVisits(userId: Int){
        invitesInteractor.getInvitesByUserId(userId).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                        success->
                        viewState.updatePublications(success)
                },
                {
                        error->
                        Timber.d("Error getting user visits ${error}")
                }
            ).addTo(compositeDisposable)
    }

    fun logout(){
        Prefs.user = null
        Prefs.token = ""
        router.newRootScreen(Screens.SplashScreen)
    }
}
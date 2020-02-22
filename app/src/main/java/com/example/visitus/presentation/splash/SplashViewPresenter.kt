package com.example.visitus.presentation.splash

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import com.example.visitus.entity.Profile
import com.example.visitus.model.data.storage.Prefs
import com.example.visitus.model.interactors.UserInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class SplashViewPresenter @Inject constructor(
    private val router: Router,
    private val userInteractor: UserInteractor
) : MvpPresenter<ISplashView>() {
    val disposables = CompositeDisposable()
    fun onLoginButtonClicked() {
        router.navigateTo(Screens.LoginScreen)
    }

    fun onRegisterButtonClicked() {
        router.navigateTo(Screens.NewAccountScreen)
    }


    fun getUser( ){
        println("Token ${Prefs.token}")
        userInteractor.getUserProfile()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Prefs.user = it
                    router.navigateTo(Screens.PersonProfileScreen(it))
                },
                {
                        error -> Timber.d("Get user error with token -> $error")
                }
            ).addTo(disposables)
    }
}

package com.example.visitus.presentation

import com.example.visitus.Screens
import com.example.visitus.model.data.storage.Prefs
import com.example.visitus.model.interactors.LaunchInteractor
import com.example.visitus.model.interactors.UserInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class AppLauncher @Inject constructor(
    private val userInteractor: UserInteractor,
    private val router: Router
) {

    fun coldStart() {
            if (Prefs.user == null) {
                router.newRootScreen(Screens.SplashScreen)
            }
            else {
                getUser()
            }
    }
    val disposables = CompositeDisposable()
    private fun getUser() {
        println("Token ${Prefs.token}")
        userInteractor.getUserProfile()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Prefs.user = it
                    router.newRootScreen(Screens.PersonProfileScreen(it))
                },
                {
                    error ->
                    Timber.d("Get user error with token -> $error")
                    router.newRootScreen(Screens.SplashScreen)

                }
            ).addTo(disposables)
    }
}
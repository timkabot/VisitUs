package com.example.visitus.presentation.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
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
class LoginPresenter @Inject constructor(
    private val router: Router,
    private val userInteractor: UserInteractor
) : MvpPresenter<ILoginView>() {

    private val disposables = CompositeDisposable()

    fun onLogin(login: String, password: String) {
        userInteractor.login(login, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                        Prefs.token = result.token
                        getUser()
                        viewState.showToast("Удачный логин")
                },
                {
                        error -> Timber.d("Login error -> $error")
                        viewState.showToast("Ошибка при логине $error")

                }
            ).addTo(disposables)
    }

    private fun getUser( ){
        println("Token ${Prefs.token}")
        userInteractor.getUserProfile()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Prefs.user = it
                    println("Got USER from inet ${it}")
                    viewState.showBottomNavigationView()
                    router.navigateTo(Screens.PersonProfileScreen(it))
                },
                {
                        error -> Timber.d("Get user error -> $error")
                }
            ).addTo(disposables)
    }

    fun onRegister() {
        router.navigateTo(Screens.NewAccountScreen)
    }

    fun onBackPressed() {
        router.exit()
    }

    fun onPrivacyPolicy() {
        router.navigateTo(Screens.PrivacyPolicyScreen)
    }

    fun onForgotPassword(){
        router.navigateTo(Screens.ForgotPasswordScreen)
    }
}
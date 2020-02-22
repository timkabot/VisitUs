package com.example.visitus.presentation.register.avatarInfo

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import com.example.visitus.entity.Profile
import com.example.visitus.model.data.storage.Prefs
import com.example.visitus.model.interactors.UserInteractor
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@InjectViewState
class AvatarInfoPresenter @Inject constructor(
    private val router: Router,
    private val userInteractor: UserInteractor
    )  : MvpPresenter<IAvatarInfoView>() {

    private val disposables = CompositeDisposable()

    fun onBack() {
        router.exit()
    }

    fun onNextClicked(account: Profile){
        viewState.showProgress()
        userInteractor.registerUser(account)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result -> println("RESULT -> $result")
                        userInteractor.login(account.login, account.password)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn((Schedulers.io()))
                            .subscribe(
                                {
                                    val token = it.token
                                    Prefs.token = token

                                    userInteractor.uploadAvatar(account.profilePhotos.avatar)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(
                                            {
                                                Timber.d("Succesful update avatar ${it}")
                                            },
                                            {}
                                        )

                                    userInteractor.uploadBackground(account.profilePhotos.background)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(
                                            {
                                                Timber.d("Successful update background ${it}")
                                            },
                                            {}
                                        )
                                },
                                {}
                            )
                    router.navigateTo(Screens.RegisterFinishedScreen)
                },
                {
                        error -> println("ERROR ->" + error)
                    viewState.showToast("Something went wrong, $error")
                    router.backTo(Screens.LoginScreen)
                }
            ).addTo(disposables)

    }

}
package com.example.visitus.ui.splash

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.model.data.storage.Prefs
import com.example.visitus.presentation.splash.ISplashView
import com.example.visitus.presentation.splash.SplashViewPresenter
import com.example.visitus.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash_screen.*


class SplashFragment : BaseFragment(), ISplashView {
    override val layoutRes = R.layout.fragment_splash_screen
    @InjectPresenter
    lateinit var presenter: SplashViewPresenter
    @ProvidePresenter
    fun providePresenter(): SplashViewPresenter =
        scope.getInstance(SplashViewPresenter::class.java)
    override fun onLoginButtonClicked() {
        //presenter.onLoginButtonClicked()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //checkIfProfileExists()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLoginListener()
    }

    private fun checkIfProfileExists(){
        Prefs.user?.let {
            presenter.getUser()
        }
    }

    private fun initLoginListener() {
        registerSplashButton.setOnClickListener {
            presenter.onRegisterButtonClicked()
        }
        loginSplashButton.setOnClickListener {
            presenter.onLoginButtonClicked()
        }

    }
}
package com.example.visitus.ui.login

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.presentation.login.forgotPassword.ForgotPasswordPresenter
import com.example.visitus.presentation.login.forgotPassword.IForgotPasswordView
import com.example.visitus.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_login_forgot_password.*

class ForgotPasswordFragment : BaseFragment(), IForgotPasswordView {
    @InjectPresenter
    lateinit var presenter: ForgotPasswordPresenter
    @ProvidePresenter
    fun providePresenter(): ForgotPasswordPresenter =
        scope.getInstance(ForgotPasswordPresenter::class.java)

    override val layoutRes = R.layout.fragment_login_forgot_password
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()

        emailOrPhoneInput.hint
    }

    private fun initListeners(){
        back_button.setOnClickListener { presenter.onBack() }
    }
}
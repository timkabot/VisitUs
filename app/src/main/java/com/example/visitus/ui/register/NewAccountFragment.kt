package com.example.visitus.ui.register

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Profile
import com.example.visitus.presentation.register.INewAccountView
import com.example.visitus.presentation.register.IRegisterView
import com.example.visitus.presentation.register.NewAccountPresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.getTextFromInputLayout
import com.example.visitus.utils.isEmpty
import com.example.visitus.utils.isValidMail
import com.example.visitus.utils.isValidMobile
import kotlinx.android.synthetic.main.fragment_register_new_account.*

class NewAccountFragment : BaseFragment(), INewAccountView, IRegisterView {
    private val TAG = "NewAccountFragment"
    @InjectPresenter
    lateinit var presenter: NewAccountPresenter

    @ProvidePresenter
    fun providePresenter(): NewAccountPresenter =
       scope.getInstance(NewAccountPresenter::class.java)

    override fun onNextClick() {
        if(checkFields()) {
            val account = Profile(
                password = getTextFromInputLayout(passwordInput),
                email = getTextFromInputLayout(emailOrLoginInput),
                phone = getTextFromInputLayout(phoneInput)
            )
            presenter.onNextClicked(account)
        }
    }

    override val layoutRes = R.layout.fragment_register_new_account
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
    }

    private fun checkFields() : Boolean
    {
        if(emailOrLoginInput.isEmpty()) {
            emailOrLoginInput.error = "Введите email"
            return false
        }
        else if(!isValidMail(getTextFromInputLayout(emailOrLoginInput))) {
            emailOrLoginInput.error = "Введите корректный email"
            return false
        }
        else if(phoneInput.isEmpty()) {
            phoneInput.error = "Введите номер телефона"
            return false
        }
        else if(!isValidMobile(getTextFromInputLayout(phoneInput))) {
            phoneInput.error = "Введите корректный номер телефона"
            return false
        }
        else if(getTextFromInputLayout(passwordInput).length < 5) {
            passwordInput.error = "Пароль должен содержать минимум 5 символов"
            return false
        }
        else if(getTextFromInputLayout(passwordInput) != getTextFromInputLayout(passwordRepeatInput))  {
            passwordInput.error = "Пароли не совпадают"
            return false
        }
        return true
    }

    private fun initListeners() {
        policyText.setOnClickListener { presenter.onPrivacyClicked() }
        back_button.setOnClickListener{ presenter.onBack() }
        nextButton.setOnClickListener { onNextClick()  }
    }

    override fun onBackPressed() {
        presenter.onBack()
    }
}
package com.example.visitus.ui.login

import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.presentation.login.ILoginView
import com.example.visitus.presentation.login.LoginPresenter
import com.example.visitus.ui.global.BaseFragment
import kotlinx.android.synthetic.main.activity_main_container.*
import kotlinx.android.synthetic.main.fragment_login_screen.*

class LoginFragment : BaseFragment(), ILoginView {

    override val layoutRes = R.layout.fragment_login_screen
    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter =
        scope.getInstance(LoginPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
    }

    override fun onPrivacyPolicy() {
        presenter.onPrivacyPolicy()
    }

    override fun onRegister() {
        presenter.onRegister()
    }

    override fun onLogin() {
        presenter.onLogin(loginInput.editText?.text.toString(),passwordTextInputLayout.editText?.text.toString())
    }

    override fun showBottomNavigationView(){
        activity!!.bottomNavigationView.visibility = View.VISIBLE
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun initListeners() {
        registerTextView.setOnClickListener { onRegister() }
        back_button.setOnClickListener { onBackPressed() }
        forgotPasswordTextView.setOnClickListener { presenter.onForgotPassword() }
        loginButton.setOnClickListener {
            if(checkFields()) onLogin()
        }
    }

    private fun checkFields() : Boolean{
        if(loginInput.editText?.text.toString() == "" || loginInput.editText == null){
            loginInput.error = "Введите логин"
            return false
        }

        if(passwordTextInputLayout.editText?.text.toString() == "" || passwordTextInputLayout.editText == null || passwordTextInputLayout.editText?.text.toString().length < 5){
            passwordTextInputLayout.error = "Пароль должен состоять минимум из пяти символов"
            return false
        }

        return true
    }

    override fun onBackPressed() {
        presenter.onBackPressed()

    }
}
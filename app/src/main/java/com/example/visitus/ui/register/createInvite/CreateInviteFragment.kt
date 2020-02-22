package com.example.visitus.ui.register.createInvite

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.presentation.register.createInvite.CreateinvitePresenter
import com.example.visitus.presentation.register.createInvite.ICreateInviteView
import com.example.visitus.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_register_finished.*

class CreateInviteFragment : BaseFragment(), ICreateInviteView {
    override val layoutRes = R.layout.fragment_register_finished
    @InjectPresenter
    lateinit var presenter: CreateinvitePresenter
    @ProvidePresenter
    fun providePresenter() : CreateinvitePresenter =
        scope.getInstance(CreateinvitePresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
    }
    private fun initListeners(){
        laterButton.setOnClickListener { presenter.onRegisterFinished() }
    }
}
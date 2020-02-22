package com.example.visitus.ui.invite.createInvite

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.InviteCategory
import com.example.visitus.presentation.invite.createInvite.CreateInviteStep5Presenter
import com.example.visitus.presentation.invite.createInvite.ICreateInviteView
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.ui.global.list.InviteTypesAdapter

class CreateInviteFragment5 : BaseFragment(), ICreateInviteView {
    override val layoutRes = R.layout.fragment_create_invite_step5
    lateinit var inviteTypes: ArrayList<InviteCategory>

    @InjectPresenter
    lateinit var presenter: CreateInviteStep5Presenter

    @ProvidePresenter
    fun providePresenter(): CreateInviteStep5Presenter =
        scope.getInstance(CreateInviteStep5Presenter::class.java)

    private lateinit var adapter: InviteTypesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
    override fun updateRecyclerView(data: List<InviteCategory>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
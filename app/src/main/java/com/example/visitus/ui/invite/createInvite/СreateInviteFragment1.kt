package com.example.visitus.ui.invite.createInvite

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.Screens
import com.example.visitus.entity.Invite
import com.example.visitus.entity.InviteCategory
import com.example.visitus.presentation.invite.createInvite.CreateInviteStep1Presenter
import com.example.visitus.presentation.invite.createInvite.ICreateInviteView
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.ui.global.list.InviteTypesAdapter
import kotlinx.android.synthetic.main.fragment_create_invite_step1.*
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class CreateVisitFragment1 : BaseFragment(), ICreateInviteView{
    override val layoutRes = R.layout.fragment_create_invite_step1
    private lateinit var gridLayoutManager: GridLayoutManager
    lateinit var inviteTypes: ArrayList<InviteCategory>
    @Inject
    lateinit var router: Router

    val visit = Invite()
    @InjectPresenter
    lateinit var presenter: CreateInviteStep1Presenter

    @ProvidePresenter
    fun providePresenter(): CreateInviteStep1Presenter =
        scope.getInstance(CreateInviteStep1Presenter::class.java)

    private lateinit var adapter: InviteTypesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initVisitTypesRecycler()
        presenter.updateRecycler()
        Toothpick.inject(this, scope)
        initListeners()
    }

    private fun initListeners(){
        back_button.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initVisitTypesRecycler(){
        gridLayoutManager = GridLayoutManager(context,3)
        visitTypesRecycler.layoutManager = gridLayoutManager
        inviteTypes = ArrayList()
        adapter = InviteTypesAdapter(inviteTypes = inviteTypes, fragment1 = this)
        visitTypesRecycler.adapter = adapter
    }

    override fun updateRecyclerView(data: List<InviteCategory>) {
        inviteTypes.clear()
        inviteTypes.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
    fun onNextClicked(){
        router.navigateTo(Screens.CreateVisit2Screen(visit))
    }

}
package com.example.visitus.ui.invite.createInvite

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Invite
import com.example.visitus.entity.InviteCategory
import com.example.visitus.presentation.invite.createInvite.CreateInviteStep4Presenter
import com.example.visitus.presentation.invite.createInvite.ICreateInviteView
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.argument
import kotlinx.android.synthetic.main.fragment_create_invite_step4.*
import timber.log.Timber


class CreateInviteFragment4 : BaseFragment(), ICreateInviteView {
    override val layoutRes = R.layout.fragment_create_invite_step4
    private val visit by argument<Invite>(VISIT_INFO, null)

    @InjectPresenter
    lateinit var presenter: CreateInviteStep4Presenter

    @ProvidePresenter
    fun providePresenter(): CreateInviteStep4Presenter =
        scope.getInstance(CreateInviteStep4Presenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
        Timber.d("Fourth create visit screen visit = $visit")

    }

    private fun initListeners(){
        nextButton.setOnClickListener {
            visit.calendarDates = calendarView.selectedDates
            println(calendarView.selectedDates)
            visit.datetime = visit.calendarDates.map { it.timeInMillis / 1000 }
            presenter.onNextClick(visit)
        }

        back_button.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
    override fun updateRecyclerView(data: List<InviteCategory>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    companion object {
        private const val VISIT_INFO = "visit_info"

        fun create(invite: Invite) =
            CreateInviteFragment4().apply {
                arguments = Bundle().apply {
                    putSerializable(VISIT_INFO, invite)
                }
            }
    }

}
package com.example.visitus.ui.invite.showInvite

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Invite
import com.example.visitus.model.data.storage.Prefs
import com.example.visitus.presentation.invite.invitePage.IInvitePage
import com.example.visitus.presentation.invite.invitePage.InvitePagePresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.argument
import com.example.visitus.utils.downloadImage
import kotlinx.android.synthetic.main.activity_main_container.*
import kotlinx.android.synthetic.main.fragment_invite_page.*
import java.util.*


class InvitePageFragment : BaseFragment(), IInvitePage{
    override val layoutRes = R.layout.fragment_invite_page
    private val invite by argument<Invite>(VISIT_INFO, null)

    @InjectPresenter
    lateinit var presenter: InvitePagePresenter

    @ProvidePresenter
    fun providePresenter() : InvitePagePresenter =
        scope.getInstance(InvitePagePresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initValues()
        initListeners()
    }

    private fun initListeners(){
        goToProfileButton.setOnClickListener {
            presenter.goToProfile(profile = invite.user)
        }
        back_button.setOnClickListener {
            onBackPressed()
        }

        acceptInviteButton.setOnClickListener {
            presenter.createVisit(invite)
        }
    }

    private fun initValues() {
        visitTitle.text = invite.title
        inviteTypeTextView.text = invite.category
        profile_image.downloadImage(invite.creatorAvatarLink)
        creatorName.text = invite.creatorName
        visitDescription.text = invite.description
        price.text = invite.price
        location.text = invite.location
        visit_background.downloadImage(invite.image)

        val calendarDates = mutableListOf<Calendar>()
        invite.datetime.forEach {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            calendarDates.add(calendar)
        }
        calendarView.selectedDates = calendarDates
        initAcceptInviteButton()
    }

    private fun initAcceptInviteButton() {
        if(invite.user.id != Prefs.user!!.id)
           presenter.checkInvite(invite.id)
        else hideAcceptButton()
    }

    companion object {
        private const val VISIT_INFO = "visit_info"
        fun create(invite: Invite) =
            InvitePageFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(VISIT_INFO, invite)
                }
            }
    }

    fun showBottomNavigationView() {
        activity!!.bottomNavigationView.visibility = View.VISIBLE
    }

    fun hideBottomNavigationView(){
        activity!!.bottomNavigationView.visibility = View.GONE
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun showAcceptButton() {
        acceptInviteButton.visibility = View.VISIBLE
    }

    override fun hideAcceptButton() {
        acceptInviteButton.visibility = View.GONE
    }
}
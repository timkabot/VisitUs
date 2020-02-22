package com.example.visitus.ui.visit.visitPage

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Visit
import com.example.visitus.presentation.visit.visitPage.IVisitView
import com.example.visitus.presentation.visit.visitPage.VisitPagePresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.argument
import com.example.visitus.utils.downloadImage
import kotlinx.android.synthetic.main.fragment_visit_page.*

class VisitPageFragment : BaseFragment(), IVisitView{
    override val layoutRes = R.layout.fragment_visit_page
    private val visit by argument<Visit>(VISIT_INFO, null)

    @InjectPresenter
    lateinit var presenter: VisitPagePresenter

    @ProvidePresenter
    fun providePresenter(): VisitPagePresenter =
        scope.getInstance(VisitPagePresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initValues()
        initListeners()
    }

    private fun initValues(){
        profile_image.downloadImage(visit.invitee.profilePhotos.avatar)
        toUserAvatar.downloadImage(visit.invitee.profilePhotos.avatar)

        fromUserAvatar.downloadImage(visit.user.profilePhotos.avatar)
        guestProfileImage.downloadImage(visit.user.profilePhotos.avatar)
        visit_background.downloadImage(visit.invite.image)
        location.text = visit.invite.location
        price.text = visit.invite.price
        visitTitle.text = visit.invite.title
        visitTypeTextView.text = visit.invite.category
        whomToWhom.text = "${visit.user.profileInfo.name}  к \n ${visit.invitee.profileInfo.name} "
        creatorName.text = visit.invitee.profileInfo.name
        guestName.text = visit.user.profileInfo.name
        visitDescription.text = visit.invite.description
    }

    private fun initListeners(){
        back_button.setOnClickListener { onBackPressed() }
        goToGuestProfileButton.setOnClickListener {
            presenter.goToProfileClicked(visit.user)
        }
        goToProfileButton.setOnClickListener {
            presenter.goToProfileClicked(visit.invitee)
        }
    }

    override fun onBackPressed() {
        presenter.onBackClicked()
    }

    companion object {
        private const val VISIT_INFO = "visit_info"
        fun create(visit: Visit) =
            VisitPageFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(VISIT_INFO, visit)
                }
            }
    }
}
package com.example.visitus.ui.invite.createInvite

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Invite
import com.example.visitus.entity.InviteCategory
import com.example.visitus.presentation.invite.createInvite.CreateInviteStep2Presenter
import com.example.visitus.presentation.invite.createInvite.ICreateInviteView
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.argument
import com.example.visitus.utils.getTextFromInputLayout
import com.example.visitus.utils.isEmpty
import kotlinx.android.synthetic.main.fragment_create_invite_step2.*
import timber.log.Timber

class CreateInviteFragment2 : BaseFragment(), ICreateInviteView {
    override val layoutRes = R.layout.fragment_create_invite_step2

    private val visit by argument<Invite>(VISIT_INFO, null)

    @InjectPresenter
    lateinit var presenter: CreateInviteStep2Presenter

    @ProvidePresenter
    fun providePresenter(): CreateInviteStep2Presenter =
        scope.getInstance(CreateInviteStep2Presenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
        Timber.d("Second create visit screen visit = $visit")
    }

    private fun initListeners() {
        nextButton.setOnClickListener {
            if(checkFields())
            {
                visit.apply {
                    price = getTextFromInputLayout(priceInput)
                    title = getTextFromInputLayout(createVisitTitle)
                    description = getTextFromInputLayout(createVisitDescription)
                    presenter.onNextClick(this)
                }
            }
        }
        back_button.setOnClickListener {
            onBackPressed()
        }
    }

    private fun checkFields() : Boolean{
        if(createVisitDescription.isEmpty()){
            createVisitDescription.error = "Введите описание"
            return false
        }

        if(createVisitTitle.isEmpty()){
            createVisitTitle.error = "Введите заголовок"
            return false
        }

        if(priceInput.isEmpty()){
            priceInput.error = "Введите цену"
            return false
        }
        return true
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
            CreateInviteFragment2().apply {
                arguments = Bundle().apply {
                    putSerializable(VISIT_INFO, invite)
                }
            }
    }

}
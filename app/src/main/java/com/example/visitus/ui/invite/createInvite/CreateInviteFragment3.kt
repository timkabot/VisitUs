package com.example.visitus.ui.invite.createInvite

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Invite
import com.example.visitus.entity.InviteCategory
import com.example.visitus.presentation.invite.createInvite.CreateInviteStep3Presenter
import com.example.visitus.presentation.invite.createInvite.ICreateInviteView
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.ui.global.list.InviteTypesAdapter
import com.example.visitus.utils.argument
import com.example.visitus.utils.getTextFromInputLayout
import com.example.visitus.utils.isEmpty
import kotlinx.android.synthetic.main.fragment_create_invite_step3.*
import timber.log.Timber

class CreateInviteFragment3 : BaseFragment(), ICreateInviteView {
    override val layoutRes = R.layout.fragment_create_invite_step3
    private val visit by argument<Invite>(VISIT_INFO, null)

    @InjectPresenter
    lateinit var presenter: CreateInviteStep3Presenter

    @ProvidePresenter
    fun providePresenter(): CreateInviteStep3Presenter =
        scope.getInstance(CreateInviteStep3Presenter::class.java)

    private lateinit var adapter: InviteTypesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
        Timber.d("Third create visit screen visit = $visit")

    }

    private fun initListeners() {
        nextButton.setOnClickListener {
            if (checkFields()) {
                visit.apply {
                    country = getTextFromInputLayout(countryInput)
                    city = getTextFromInputLayout(cityInput)
                    area = getTextFromInputLayout(locationAreaInput)
                    exactLocation = getTextFromInputLayout(exactAddressInput)
                    presenter.onNextClick(this)
                }
            }
        }

        back_button.setOnClickListener {
            onBackPressed()
        }
    }

    private fun checkFields(): Boolean {
        if (countryInput.isEmpty()) {
            countryInput.error = "Введите страну"
            return false
        }

        if (locationAreaInput.isEmpty()) {
            locationAreaInput.error = "Введите область/регион"
            return false
        }

        if (cityInput.isEmpty()) {
            cityInput.error = "Введите город"
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
            CreateInviteFragment3().apply {
                arguments = Bundle().apply {
                    putSerializable(VISIT_INFO, invite)
                }
            }
    }

}
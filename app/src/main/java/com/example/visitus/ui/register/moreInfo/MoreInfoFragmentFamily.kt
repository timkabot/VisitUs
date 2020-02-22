package com.example.visitus.ui.register.moreInfo

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Profile
import com.example.visitus.presentation.register.moreInfo.IMoreInfoView
import com.example.visitus.presentation.register.moreInfo.MoreInfoPresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.argument
import kotlinx.android.synthetic.main.fragment_register_more_info_family.*

class MoreInfoFragmentFamily : BaseFragment(), IMoreInfoView {
    override val layoutRes = R.layout.fragment_register_more_info_family
    @InjectPresenter
    lateinit var presenter: MoreInfoPresenter
    private val account by argument<Profile>(ACCOUNT_INFO, null)

    @ProvidePresenter
    fun providePresenter(): MoreInfoPresenter =
        scope.getInstance(MoreInfoPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        back_button.setOnClickListener { presenter.onBack() }
        nextButton.setOnClickListener { presenter.onNextClicked(account) }
    }

    override fun onBackPressed() {
        presenter.onBack()
    }
    companion object {
        private const val ACCOUNT_INFO = "account_info"

        fun create(account: Profile) =
            MoreInfoFragmentFamily().apply {
                arguments = Bundle().apply {
                    putSerializable(ACCOUNT_INFO, account)
                }
            }
    }
}
package com.example.visitus.ui.register.moreInfo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Profile
import com.example.visitus.presentation.register.IRegisterView
import com.example.visitus.presentation.register.moreInfo.IMoreInfoView
import com.example.visitus.presentation.register.moreInfo.MoreInfoPresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.argument
import kotlinx.android.synthetic.main.fragment_register_more_info_company.*

class MoreInfoFragmentCompany : BaseFragment(), IRegisterView, AdapterView.OnItemSelectedListener,
    IMoreInfoView {
    override val layoutRes = R.layout.fragment_register_more_info_company
    private val account by argument<Profile>(ACCOUNT_INFO, null)

    @InjectPresenter
    lateinit var presenter: MoreInfoPresenter

    @ProvidePresenter
    fun providePresenter(): MoreInfoPresenter =
        scope.getInstance(MoreInfoPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
        initOperatingModeSpinner()
    }

    private fun initOperatingModeSpinner() {
        operatingModeSpinner.onItemSelectedListener = this
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.operating_modes,
                R.layout.item_my_spinner
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                operatingModeSpinner.adapter = adapter
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
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
            MoreInfoFragmentCompany().apply {
                arguments = Bundle().apply {
                    putSerializable(ACCOUNT_INFO, account)
                }
            }
    }
}
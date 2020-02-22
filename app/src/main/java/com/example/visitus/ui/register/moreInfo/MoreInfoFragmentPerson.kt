package com.example.visitus.ui.register.moreInfo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Profile
import com.example.visitus.presentation.register.moreInfo.IMoreInfoView
import com.example.visitus.presentation.register.moreInfo.MoreInfoPresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.argument
import com.example.visitus.utils.getTextFromInputLayout
import com.example.visitus.utils.isEmpty
import kotlinx.android.synthetic.main.fragment_register_more_info_person.*

class MoreInfoFragmentPerson : BaseFragment(), AdapterView.OnItemSelectedListener, IMoreInfoView {
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val selectedItem = p0?.getItemAtPosition(p2).toString()
        account.profileInfo.maritalStatus = selectedItem
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private val account by argument<Profile>(ACCOUNT_INFO, null)

    @InjectPresenter
    lateinit var presenter: MoreInfoPresenter

    @ProvidePresenter
    fun providePresenter(): MoreInfoPresenter =
        scope.getInstance(MoreInfoPresenter::class.java)

    override val layoutRes = R.layout.fragment_register_more_info_person
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println(account)
        initMaritalStatusSpinner()
        initListeners()
    }
    private fun onNextClicked(){
        account.profileInfo.activity = getTextFromInputLayout(fieldOfActivityInput)
        account.profileInfo.aboutMe = getTextFromInputLayout(aboutMeInput)
        account.profileInfo.education = getTextFromInputLayout(educationInput)
        presenter.onNextClicked(account)
    }
    private fun initListeners() {
        back_button.setOnClickListener { presenter.onBack() }
        nextButton.setOnClickListener {
            if(checkFields())
            { onNextClicked() }
        }
    }

    private fun initMaritalStatusSpinner() {
        marialStatusSpinner.onItemSelectedListener = this
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.marial_statuses,
                R.layout.item_my_spinner
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                marialStatusSpinner.adapter = adapter
            }
        }
    }
    private fun checkFields() : Boolean
    {
        return when {
            fieldOfActivityInput.isEmpty() -> {
                fieldOfActivityInput.error = "Введите род деятельности"
                false
            }
            aboutMeInput.isEmpty() -> {
                aboutMeInput.error = "Расскажите о себе"
                false
            }
            educationInput.isEmpty() -> {
                educationInput.error = "Расскажите о вашем образовании"
                false
            }
            else -> true
        }
    }
    companion object {
        private const val ACCOUNT_INFO = "account_info"

        fun create(account: Profile) =
            MoreInfoFragmentPerson().apply {
                arguments = Bundle().apply {
                    putSerializable(ACCOUNT_INFO, account)
                }
            }
    }
}
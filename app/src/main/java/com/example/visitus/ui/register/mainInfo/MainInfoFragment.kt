package com.example.visitus.ui.register.mainInfo

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Profile
import com.example.visitus.model.data.storage.Prefs
import com.example.visitus.presentation.register.IRegisterView
import com.example.visitus.presentation.register.mainInfo.IMainInfoView
import com.example.visitus.presentation.register.mainInfo.MainInfoPresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.*
import kotlinx.android.synthetic.main.fragment_register_main_info.*
import timber.log.Timber
import java.util.*


class MainInfoFragment : BaseFragment(), IMainInfoView, IRegisterView,
    AdapterView.OnItemSelectedListener {

    private val account by argument<Profile>(ACCOUNT_INFO, null)

    @InjectPresenter
    lateinit var presenter: MainInfoPresenter

    @ProvidePresenter
    fun providePresenter(): MainInfoPresenter =
        scope.getInstance(MainInfoPresenter::class.java)

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val selectedItem = p0?.getItemAtPosition(p2).toString()
        presenter.updateProfileType(selectedItem)
    }


    override val layoutRes = R.layout.fragment_register_main_info

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSpinner()
        initListeners()
        presenter.account = account
        Timber.d("Account on main info step ${presenter.account}")
    }

    private val listener = OnSelectDateListener {
        val birthDateTimeStamp = it[0].timeInMillis
        val birthDateCalendar = getCalendarFromTimeStamp(birthDateTimeStamp)
        println(birthDateCalendar.time)
        Prefs.user?.profileInfo?.birthDate = birthDateTimeStamp.toString()
        birthDateEditText.setText(birthDateCalendar.time.formatToViewDateDefaults())
    }

    private fun showDatePickerDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in TextView
            birthDateEditText.setText("$dayOfMonth.$monthOfYear.$year")
        }, year, month, day)
        dpd.show()

    }

    private fun onNextClicked() {
        if (checkFields()) {
            presenter.account.login = getTextFromInputLayout(loginInput)
            presenter.account.profileInfo.name = getTextFromInputLayout(nameSurnameInput)

            val selectedId = genderInput.checkedRadioButtonId
            if (selectedId != -1) {
                val radio: RadioButton? = view?.findViewById(selectedId)
                if (radio != null) {
                    presenter.account.profileInfo.gender = radio.text.toString()
                }
            }
            presenter.account.profileInfo.birthDate = getTextFromInputLayout(birthdateInput)
            presenter.account.profileInfo.city = getTextFromInputLayout(cityInput)
            presenter.account.profileInfo.address = getTextFromInputLayout(addressInput)
            presenter.onNextClicked()
        }

    }

    private fun initListeners() {
        back_button.setOnClickListener { presenter.onBack() }
        nextButton.setOnClickListener { onNextClicked() }
        //birthDateEditText.setOnClickListener {}
        birthDateEditText.setOnFocusChangeListener { _, b ->
            if(b) showDatePickerDialog()
        }

        }


    private fun initSpinner() {
        profileTypeSpinner.onItemSelectedListener = this
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.account_types,
                R.layout.item_my_spinner
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                profileTypeSpinner.adapter = adapter
            }
        }
    }

    override fun hideGenderSection() {
        genderTextView.visibility = View.GONE
        genderInput.visibility = View.GONE
    }

    override fun showGenderSection() {
        genderTextView.visibility = View.VISIBLE
        genderInput.visibility = View.VISIBLE
    }

    override fun hideBirthDateSection() {
        birthdateTextView.visibility = View.GONE
        birthdateInput.visibility = View.GONE
    }

    override fun showBirthDateSection() {
        birthdateTextView.visibility = View.VISIBLE
        birthdateInput.visibility = View.VISIBLE
    }

    override fun hideFamilyCountSection() {
        familyCountTextView.visibility = View.GONE
        familyCountInput.visibility = View.GONE
    }

    override fun showFamilyCountSection() {
        familyCountTextView.visibility = View.VISIBLE
        familyCountInput.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        presenter.onBack()
    }

    private fun checkFields(): Boolean {
        when {
            loginInput.isEmpty() -> {
                loginInput.error = "Введите логин"
                return false
            }
            nameSurnameInput.isEmpty() -> {
                nameSurnameInput.error = "Введите ФИО"
                return false
            }
            birthdateInput.isEmpty() -> {
                birthdateInput.error = "Введите дату рождения"
                return false
            }
            getTextFromInputLayout(birthdateInput).length != 10 -> {
                birthdateInput.error = "Введите корректную дату DD.MM.YYY"
                return false
            }
            else -> {
                val selectedId = genderInput.checkedRadioButtonId
                if (selectedId == -1) {
                    Toast.makeText(context, "Выберите пол", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }
        return true
    }


    companion object {
        private const val ACCOUNT_INFO = "account_info"

        fun create(account: Profile) =
            MainInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ACCOUNT_INFO, account)
                }
            }
    }
}
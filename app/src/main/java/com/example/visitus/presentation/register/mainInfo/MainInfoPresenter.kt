package com.example.visitus.presentation.register.mainInfo

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import com.example.visitus.entity.Profile
import com.example.visitus.entity.ProfileType.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainInfoPresenter @Inject constructor(
    private val router: Router
) : MvpPresenter<IMainInfoView>() {

    lateinit var account: Profile

    fun updateProfileType(accountType: String) {
        when (accountType) {
            "Компания" -> {
                viewState.apply {
                    hideGenderSection()
                    hideBirthDateSection()
                    hideFamilyCountSection()
                    account.profileType = COMPANY
                }
            }
            "Семейный" -> {
                viewState.apply {
                    showFamilyCountSection()
                    hideBirthDateSection()
                    hideGenderSection()
                    account.profileType = FAMILY
                }
            }
            "Личный" -> {
                viewState.apply {
                    showGenderSection()
                    showBirthDateSection()
                    hideFamilyCountSection()
                    account.profileType = PRIVATE
                }
            }
        }
    }

    fun onNextClicked(){
        println("Trying to live from maininfo {$account}")
        when(account.profileType){
            PRIVATE  -> router.navigateTo(Screens.RegisterMoreInfoFragmentPerson(account))
            COMPANY -> router.navigateTo(Screens.RegisterMoreInfoCompanyScreen(account))
            FAMILY  -> router.navigateTo(Screens.RegisterMoreInfoFragmentFamily(account))
        }
    }

    fun onBack() {
        router.exit()
    }
}
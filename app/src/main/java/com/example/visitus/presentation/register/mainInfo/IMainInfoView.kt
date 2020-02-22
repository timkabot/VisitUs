package com.example.visitus.presentation.register.mainInfo

import com.arellomobile.mvp.MvpView

interface IMainInfoView : MvpView {
    fun hideGenderSection()
    fun showGenderSection()

    fun hideBirthDateSection()
    fun showBirthDateSection()

    fun hideFamilyCountSection()
    fun showFamilyCountSection()
}
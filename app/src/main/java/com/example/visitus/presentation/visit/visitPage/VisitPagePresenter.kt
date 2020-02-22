package com.example.visitus.presentation.visit.visitPage

import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.Screens
import com.example.visitus.entity.Profile
import com.example.visitus.model.interactors.VisitsInteractor
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import javax.inject.Inject

class VisitPagePresenter @Inject constructor(
    private val router: Router,
    private val visitsInteractor: VisitsInteractor
) : MvpPresenter<IVisitView>(){
    fun onVisitCancelClicked(){

    }

    fun onBackClicked(){
        router.exit()
    }

    fun goToProfileClicked(profile: Profile){
        router.navigateTo(Screens.PersonProfileScreen(profile))
    }
}
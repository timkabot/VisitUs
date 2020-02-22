package com.example.visitus.presentation.visit

import com.arellomobile.mvp.MvpView
import com.example.visitus.entity.Visit

interface IVisitsListView : MvpView {
    fun updateVisits(visits : ArrayList<Visit>)
}
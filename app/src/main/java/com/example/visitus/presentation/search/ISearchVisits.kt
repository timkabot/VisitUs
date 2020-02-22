package com.example.visitus.presentation.search

import com.arellomobile.mvp.MvpView
import com.example.visitus.entity.Invite

interface ISearchVisits : MvpView {
    fun updateRecyclerView(data : List<Invite>)

}
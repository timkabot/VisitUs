package com.example.visitus.presentation.invite.createInvite

import com.arellomobile.mvp.MvpView
import com.example.visitus.entity.InviteCategory

interface ICreateInviteView : MvpView
{
    fun updateRecyclerView(data : List<InviteCategory>)
    //fun showProgressVar()
    //fun hideProgressBar()
}
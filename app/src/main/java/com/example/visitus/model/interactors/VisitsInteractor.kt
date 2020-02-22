package com.example.visitus.model.interactors

import com.example.visitus.entity.CheckInviteForVisitRequest
import com.example.visitus.entity.CreateVisitRequest
import com.example.visitus.model.data.server.ApiService
import javax.inject.Inject

class VisitsInteractor @Inject constructor(private val apiService: ApiService) {
    fun checkInviteForVisit(inviteId: Int) = apiService.checkInviteForVisit(CheckInviteForVisitRequest(inviteId))
    fun getVisitsToMe() = apiService.getVisitsToMe()
    fun getVisitsFromMe() = apiService.getVisitsFromMe()
    fun createVisit(inviteId: Int) = apiService.createVisit(CreateVisitRequest(inviteId))

}
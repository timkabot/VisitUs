package com.example.visitus.model.interactors

import com.example.visitus.entity.GetInvitesByUserIdRequest
import com.example.visitus.entity.Location
import com.example.visitus.entity.NetworkResponse
import com.example.visitus.entity.Invite
import com.example.visitus.model.data.server.ApiService
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class InvitesInteractor @Inject constructor(private val apiService: ApiService) {
    fun getInvites(city : String) = apiService.getinvitesByLocation(Location(city))
    fun getInviteTypes() = apiService.getCategories()
    fun createInvite(invite: Invite) = apiService.createInvite(invite)

    fun uploadImage(inviteId: Int,
                    pathImage: String) : Single<NetworkResponse>
    {
        val file = File(pathImage)
        val reqFile : RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part2 = MultipartBody.Part.createFormData("photo", file.name, reqFile)
        val part3 = MultipartBody.Part.createFormData("main_photo", file.name, reqFile)
        return apiService.addInvitePhoto(inviteId, part2, part3)
    }

    fun getUserInvites() = apiService.getInvites()

    fun getFacilities() = apiService.getFacilities()

    fun getInvitesByUserId(userId: Int) = apiService.getInvitesByUserId(GetInvitesByUserIdRequest(userId))
}
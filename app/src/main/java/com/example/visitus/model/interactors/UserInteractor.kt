package com.example.visitus.model.interactors

import com.example.visitus.entity.LoginRequest
import com.example.visitus.entity.PhotoUploadResponse
import com.example.visitus.entity.Profile
import com.example.visitus.model.data.server.ApiService
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UserInteractor @Inject constructor(private val apiService: ApiService) {
    fun registerUser(
        user: Profile
    ) = apiService.registerUser(user)

    fun login(
        login: String,
        password: String
    ) = apiService.login(LoginRequest(login, password))

    fun getUserProfile(

    ) = apiService.getUserProfile()

    fun uploadAvatar(filePath: String) : Single<PhotoUploadResponse>
    {
        val file = File(filePath)
        val reqFile : RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("avatar", file.name, reqFile)

        return apiService.uploadAvatar(part)
    }

    fun uploadBackground(filePath: String) : Single<PhotoUploadResponse>
    {
        val file = File(filePath)
        val reqFile : RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("background", file.name, reqFile)

        return apiService.uploadBackground(part)
    }
}
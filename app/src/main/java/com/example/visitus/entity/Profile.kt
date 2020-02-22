package com.example.visitus.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profile(
    @SerializedName("profile_type")
    var profileType: ProfileType = ProfileType.PRIVATE,
    var token: String = "",
    var email: String = "",
    var login: String = "",
    var phone: String = "",
    var password: String = "",
    var id : Int = -1,
    @SerializedName("profile_info")
    var profileInfo: ProfileInfo = ProfileInfo(),
    @SerializedName("profile_photos")
    var profilePhotos: ProfilePhotos = ProfilePhotos()
) : Serializable
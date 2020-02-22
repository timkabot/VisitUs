package com.example.visitus.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ProfileInfo(
    var name: String = "",
    @SerializedName("about_me")
    var aboutMe: String = "",
    var gender: String = "",
    var city: String = "",
    var address: String = "",
    @SerializedName("birth_date")
    var birthDate: String = "",
    @SerializedName("marital_status")
    var maritalStatus: String = "",
    var education: String = "",
    var activity: String = ""
) : Serializable
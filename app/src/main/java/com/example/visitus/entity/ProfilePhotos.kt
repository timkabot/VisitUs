package com.example.visitus.entity

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfilePhotos (
    var avatar: String = "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png",
    @SerializedName("background")
    var background: String = "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png"
    //TODO
    //var avatar: Bitmap? = null,
    //var wallpaper: Bitmap? = null
) : Serializable
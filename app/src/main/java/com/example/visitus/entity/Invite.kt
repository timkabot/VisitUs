package com.example.visitus.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Invite(
    var status: String = "",
    var flagLink: String = "https://cdn.shopify.com/s/files/1/0707/3783/products/russian-federation-flag.jpg",
    var price: String = "0$",
    var id: Int = 0,
    @SerializedName("user_id")
    var userId: Int = 1,
    var date: String = "12.01.2020",
    var title: String = "Тайны императорского собора",
    var description: String = "Величественное сооружение |X века",
    var category : String = "КУЛЬТУРНО-ПОЗНАВАТЕЛЬНЫЙ",
    var conditions: String = "",
    var maxGuestNumber: Int = 1,
    var area: String = "",
    var city: String = "Казань",
    var country: String = "Россия",
    var exactLocation: String = "",
    var coordinates : Coordinate = Coordinate(),
    var facilities : MutableList<Facilities> = mutableListOf(),
    var food: MutableList<Int> = mutableListOf(),
    @SerializedName("photo")
    var image: String = "",
    var imageFilePath: String = "",
    var user : Profile = Profile(),
    var calendarDates : List<Calendar> = listOf(),
    var datetime: List<Long> = listOf(),
    var location: String = ""
) : Serializable {
    val creatorAvatarLink: String
        get() = user.profilePhotos.avatar

    val creatorUsername: String
        get() = user.login

    val creatorName: String
        get() = user.profileInfo.name

}
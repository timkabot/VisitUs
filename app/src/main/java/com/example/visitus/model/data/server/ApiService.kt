package com.example.visitus.model.data.server

import com.example.visitus.entity.*
import com.example.visitus.model.data.storage.Prefs
import com.example.visitus.utils.Constants
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {

    @POST("user/register")
    fun registerUser(@Body profile: Profile): Single<RegisterResponse>

    @POST("user/login")
    fun login(@Body loginRequest: LoginRequest): Single<LoginResponse>

    @POST("invites/getByLocation")
    fun getinvitesByLocation(@Body location: Location): Single<ArrayList<Invite>>

    @POST("invites/create")
    fun createInvite(@Body invite: Invite) : Single<CreateInviteResponse>

    @Multipart
    @POST("invites/addPhotos")
    fun addInvitePhoto(@Part("invite_id") invite_id: Int,
                       @Part main_photo: MultipartBody.Part,
                       @Part photo: MultipartBody.Part) : Single<NetworkResponse>

    @GET("invites/categories")
    fun getCategories() : Single<ArrayList<InviteCategory>>

    @POST("user/invites")
    fun getInvites() : Single<ArrayList<Invite>>

    @POST("user/get")
    fun getUserProfile() : Single<Profile>

    @Multipart
    @POST("user/photos/avatar/upload")
    fun uploadAvatar(@Part file: MultipartBody.Part ) : Single<PhotoUploadResponse>

    @Multipart
    @POST("user/photos/background/upload")
    fun uploadBackground(@Part file: MultipartBody.Part ) : Single<PhotoUploadResponse>

    @POST("invites/getByUserId")
    fun getInvitesByUserId(@Body invitesRequest: GetInvitesByUserIdRequest) : Single<ArrayList<Invite>>

    @POST("visits/create")
    fun createVisit(@Body createVisitRequest: CreateVisitRequest) : Single<NetworkResponse>

    @GET("invites/facilities")
    fun getFacilities() : Single<ArrayList<Facilities>>

    @POST("visits/checkInvite")
    fun checkInviteForVisit(@Body inviteId: CheckInviteForVisitRequest) : Single<CheckInviteResponse>

    @POST("visits/getVisitsToMe")
    fun getVisitsToMe() : Single<ArrayList<Visit>>

    @POST("visits/getMyVisits")
    fun getVisitsFromMe() : Single<ArrayList<Visit>>
    /**
     * Companion object to create the GetShopApi
     */

    companion object Factory {

        private fun createNetworkClient(isDebug: Boolean = true): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level =
                    if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
            return OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addNetworkInterceptor(createAccessTokenProvidingInterceptor())
                .build()
        }

        fun create(baseUrl: String = Constants.BASEURL): ApiService {

            val gson = GsonBuilder() // Add null defense Adapter
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .setLenient()
                .serializeNulls()
                .create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(createNetworkClient())
                .build()
            return retrofit.create(ApiService::class.java)
        }

        private val accessToken: String
            get() = Prefs.token
        private fun createAccessTokenProvidingInterceptor() = Interceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build())
        }
    }
}

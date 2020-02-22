package com.example.visitus.ui.profile

import android.os.Bundle
import com.example.visitus.R
import com.example.visitus.entity.Profile
import com.example.visitus.model.data.storage.Prefs
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.argument
import kotlinx.android.synthetic.main.fragment_about_me.*

class AboutMeFragment : BaseFragment(){
    override val layoutRes = R.layout.fragment_about_me
    private val profile by argument<Profile>(PROFILE_INFO, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initInfo(profile = profile )
    }
    private fun initInfo(profile: Profile){
        profile.let {
            genderAndAge.text = it.profileInfo.gender
            location.text = " " + it.profileInfo.city + ", " + it.profileInfo.address
            maritalStatus.text = it.profileInfo.maritalStatus
            birthDate.text = it.profileInfo.birthDate
            education.text = it.profileInfo.education
            email.text = it.email
            aboutMe.text = it.profileInfo.aboutMe
        }
    }

    companion object {
        private const val PROFILE_INFO = "profile_info"
        fun create(profile: Profile) =
            AboutMeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PROFILE_INFO, profile)
                }
            }
    }
}
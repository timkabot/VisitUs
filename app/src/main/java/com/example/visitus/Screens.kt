package com.example.visitus

import com.example.visitus.entity.Profile
import com.example.visitus.entity.Invite
import com.example.visitus.entity.Visit
import com.example.visitus.ui.invite.createInvite.*
import com.example.visitus.ui.login.ForgotPasswordFragment
import com.example.visitus.ui.login.LoginFragment
import com.example.visitus.ui.privacypolicy.PrivacyPolicyFragment
import com.example.visitus.ui.profile.AboutMeFragment
import com.example.visitus.ui.profile.PersonProfileFragment
import com.example.visitus.ui.register.*
import com.example.visitus.ui.register.avatarInfo.AvatarInfoFragment
import com.example.visitus.ui.register.createInvite.CreateInviteFragment
import com.example.visitus.ui.register.mainInfo.MainInfoFragment
import com.example.visitus.ui.register.moreInfo.MoreInfoFragmentCompany
import com.example.visitus.ui.register.moreInfo.MoreInfoFragmentFamily
import com.example.visitus.ui.register.moreInfo.MoreInfoFragmentPerson
import com.example.visitus.ui.search.SearchVisitsFragment
import com.example.visitus.ui.splash.SplashFragment
import com.example.visitus.ui.visit.profileVisits.ProfileVisits
import com.example.visitus.ui.invite.showInvite.InvitePageFragment
import com.example.visitus.ui.visit.visitPage.VisitPageFragment
import com.example.visitus.ui.visit.visitsFromMe.VisitsFromMeFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object LoginScreen : SupportAppScreen() {
        override fun getFragment() = LoginFragment()
    }
    object SplashScreen : SupportAppScreen() {
        override fun getFragment() = SplashFragment()
    }

    object PrivacyPolicyScreen : SupportAppScreen() {
        override fun getFragment() = PrivacyPolicyFragment()
    }

    object NewAccountScreen : SupportAppScreen() {
        override fun getFragment() = NewAccountFragment()
    }

    data class RegisterMainInfoScreen(private val account: Profile) : SupportAppScreen() {
        override fun getFragment() = MainInfoFragment.create(account)
    }

    data class RegisterMoreInfoCompanyScreen(private val account: Profile) : SupportAppScreen() {
        override fun getFragment() =
            MoreInfoFragmentCompany.create(account)
    }

    data class RegisterMoreInfoFragmentFamily(private val account: Profile) : SupportAppScreen() {
        override fun getFragment() =
            MoreInfoFragmentFamily.create(account)
    }
    data class RegisterMoreInfoFragmentPerson(private val account: Profile) : SupportAppScreen() {
        override fun getFragment() =
            MoreInfoFragmentPerson.create(account)
    }
    data class  RegisterAvatarInfoFragment(private val account: Profile) : SupportAppScreen(){
        override fun getFragment() =
            AvatarInfoFragment.create(account)
    }
    object RegisterFinishedScreen : SupportAppScreen(){
        override fun getFragment() = CreateInviteFragment()
    }

    object ForgotPasswordScreen : SupportAppScreen(){
        override fun getFragment() = ForgotPasswordFragment()
    }

    class PersonProfileScreen(private val profile: Profile) : SupportAppScreen() {
        override fun getFragment() = PersonProfileFragment.create(profile = profile)
    }

    class AboutMeFragmentScreen(private val profile: Profile) : SupportAppScreen() {
        override fun getFragment() = AboutMeFragment.create(profile = profile)
    }

    object SearchVisitsScreen : SupportAppScreen() {
        override fun getFragment() = SearchVisitsFragment()
    }

    object CreateVisit1Screen : SupportAppScreen() {
        override fun getFragment() = CreateVisitFragment1()
    }
    class CreateVisit2Screen(private val invite: Invite) : SupportAppScreen() {
        override fun getFragment() =
            CreateInviteFragment2.create(invite)
    }
    class CreateVisit3Screen(private val invite: Invite) : SupportAppScreen() {
        override fun getFragment() =
            CreateInviteFragment3.create(invite)
    }
    class CreateVisit4Screen(private val invite: Invite) : SupportAppScreen() {
        override fun getFragment() =
            CreateInviteFragment4.create(invite)
    }
    object CreateVisit5Screen : SupportAppScreen() {
        override fun getFragment() = CreateInviteFragment5()
    }

    class CreateVisitImageUploadScreen(private val invite: Invite) : SupportAppScreen() {
        override fun getFragment() = CreateInviteUploadImageFragment.create(invite)
    }

    class ShowInviteScreen(private val invite: Invite) : SupportAppScreen() {
        override fun getFragment() = InvitePageFragment.create(invite)

    }

    object ProfileVisitsScreen : SupportAppScreen(){
        override fun getFragment() = ProfileVisits()
    }

    object VisitsToMeScreen : SupportAppScreen(){
        override fun getFragment() = com.example.visitus.ui.visit.visitsToMe.VisitsToMeFragment()

    }

    object VisitsFromMeScreen : SupportAppScreen(){
        override fun getFragment() = VisitsFromMeFragment()
    }

    class VisitPageScreen(private val visit: Visit) : SupportAppScreen(){
        override fun getFragment() = VisitPageFragment.create(visit = visit)
    }

}
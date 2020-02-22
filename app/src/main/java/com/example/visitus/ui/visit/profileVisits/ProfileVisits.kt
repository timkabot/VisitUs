package com.example.visitus.ui.visit.profileVisits

import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter
import com.example.visitus.R
import com.example.visitus.Screens
import com.example.visitus.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_global.*

class ProfileVisits : BaseFragment(){
    override val layoutRes = R.layout.fragment_global

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewPagerAdapter = ViewPagerAdapter()
        viewpager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewpager)
    }

    private inner class ViewPagerAdapter : FragmentPagerAdapter(childFragmentManager) {
        override fun getItem(position: Int): BaseFragment = when (position) {
            MY_VISITS -> {Screens.VisitsFromMeScreen.fragment} //Screens.AboutMeFragmentScreen(profile).fragment
            VISITS_TO_ME -> {Screens.VisitsToMeScreen.fragment} //Screens.IssueInfo.fragment
            else -> {Screens.VisitsFromMeScreen.fragment}
        }

        override fun getCount() = 2

        override fun getPageTitle(position: Int) = when (position) {
            MY_VISITS -> "Мои визиты"
            VISITS_TO_ME -> "Визиты ко мне"
            else -> null
        }
    }

    companion object {
        private const val MY_VISITS = 0
        private const val VISITS_TO_ME = 1
    }
}
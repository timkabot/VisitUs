package com.example.visitus.ui.profile

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.Screens
import com.example.visitus.entity.Profile
import com.example.visitus.entity.Invite
import com.example.visitus.model.data.storage.Prefs
import com.example.visitus.presentation.profile.personProfile.IPersonProfileView
import com.example.visitus.presentation.profile.personProfile.PersonProfilePresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.ui.global.list.PersonalnvitesAdapter
import com.example.visitus.utils.argument
import com.example.visitus.utils.downloadImage
import com.example.visitus.utils.setImageWithGlide
import kotlinx.android.synthetic.main.activity_main_container.*
import kotlinx.android.synthetic.main.fragment_profile_personal.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList


class PersonProfileFragment : BaseFragment(), IPersonProfileView{
    override fun updatePublications(invites : ArrayList<Invite>) {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        invitesRecycler.layoutManager = linearLayoutManager

        val adapter = PersonalnvitesAdapter(invites)
        invitesRecycler.adapter = adapter
    }
    private val profile by argument<Profile>(PROFILE_INFO, null)

    private val PICK_AVATAR_REQUEST = 1
    private val PICK_BACKGROUND_REQUEST = 2
    override val layoutRes = R.layout.fragment_profile_personal

    @InjectPresenter
    lateinit var presenter: PersonProfilePresenter

    private lateinit var linearLayoutManager: LinearLayoutManager


    @ProvidePresenter
    fun providePresenter() : PersonProfilePresenter =
        scope.getInstance(PersonProfilePresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(invitesRecycler)

        if( activity!!.bottomNavigationView.visibility != View.VISIBLE){
            activity!!.bottomNavigationView.visibility = View.VISIBLE
        }

        println("Profile  === $profile")


        initListeners()

        initInfo()
        presenter.getUserVisits(profile.id)

        val viewPagerAdapter = ViewPagerAdapter()
        viewpager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewpager)
    }

    private fun initListeners(){
        if(profile.login ==  Prefs.user?.login ) {
            profile_image.setOnClickListener {
                chooseAvatar()
            }

            visit_background.setOnClickListener {
                chooseBackground()
            }
        }

        logoutButton.setOnClickListener {
            presenter.logout()
            activity!!.bottomNavigationView.visibility = View.GONE

        }
    }

    private fun chooseBackground() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_BACKGROUND_REQUEST)
    }

    private fun chooseAvatar() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_AVATAR_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
            when (requestCode) {
                PICK_AVATAR_REQUEST -> {
                    profile_image.setImageWithGlide(bitmap)
                    val avatarPath =saveImageToInternalStorage(bitmap)
                    presenter.uploadAvatar(avatarPath)
                    Prefs.user!!.profilePhotos.avatar = avatarPath
                }
                PICK_BACKGROUND_REQUEST -> {
                    visit_background.setImageWithGlide(bitmap)
                    val avatarBackgroundPath = saveImageToInternalStorage(bitmap)
                    presenter.uploadBackground(avatarBackgroundPath)
                    println(avatarBackgroundPath)
                    Prefs.user!!.profilePhotos.background = avatarBackgroundPath
                }
            }

        }
    }

    private fun initInfo(){
        nameSurname.text = profile.profileInfo.name
        login.text = profile.login
        location.text = profile.profileInfo.city

        profile.profilePhotos.avatar?.let { link->
            profile_image.downloadImage(link)
        }
        profile.profilePhotos.background?.let { link->
            visit_background.downloadImage(link)
        }
    }

    private inner class ViewPagerAdapter : FragmentPagerAdapter(childFragmentManager) {
        override fun getItem(position: Int): BaseFragment = when (position) {
            ABOUT_ME -> Screens.AboutMeFragmentScreen(profile).fragment
            //TAB_INFO -> Screens.IssueInfo.fragment
            else -> { Screens.AboutMeFragmentScreen(profile).fragment}
        }

        override fun getCount() = 3

        override fun getPageTitle(position: Int) = when (position) {
            ABOUT_ME -> "About Me"
            else -> null
        }
    }

    // Method to save an image to internal storage
    private fun saveImageToInternalStorage(bitmap: Bitmap): String {

        // Get the context wrapper instance
        val wrapper = ContextWrapper(activity!!.applicationContext)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)


        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return file.absolutePath
    }

    override fun onResume() {
        super.onResume()
        presenter.getUserVisits(profile.id)
        initInfo()

    }
    companion object {
        private const val ABOUT_ME = 0
        private const val MAP = 1
        private const val OTZYVY = 2

        private const val PROFILE_INFO = "profile_info"
        fun create(profile: Profile) =
            PersonProfileFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PROFILE_INFO, profile)
                }
            }
    }
}
package com.example.visitus.ui.register.avatarInfo

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Profile
import com.example.visitus.presentation.register.avatarInfo.AvatarInfoPresenter
import com.example.visitus.presentation.register.avatarInfo.IAvatarInfoView
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.argument
import com.example.visitus.utils.getResizedBitmap
import com.example.visitus.utils.setImageWithGlide
import kotlinx.android.synthetic.main.fragment_register_photo_upload.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AvatarInfoFragment : BaseFragment(), IAvatarInfoView {
    override val layoutRes = R.layout.fragment_register_photo_upload
    private val PICK_AVATAR_REQUEST = 1
    private val PICK_BACKGROUND_REQUEST = 2

    @InjectPresenter
    lateinit var presenter: AvatarInfoPresenter
    private val account by argument<Profile>(ACCOUNT_INFO, null)

    @ProvidePresenter
    fun providePresenter(): AvatarInfoPresenter =
        scope.getInstance(AvatarInfoPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
        println(account)
    }

    private fun chooseAvatar() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_AVATAR_REQUEST)
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun chooseAvatarBackground() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_BACKGROUND_REQUEST
        )
    }

    private fun initListeners() {
        profile_image.setOnClickListener { chooseAvatar() }
        visit_background.setOnClickListener { chooseAvatarBackground() }
        back_button.setOnClickListener { presenter.onBack() }
        nextButton.setOnClickListener{
            presenter.onNextClicked(account) }
    }

    override fun showProgress(){
        progress_horizontal.visibility = View.VISIBLE
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

    override fun onBackPressed() {
        presenter.onBack()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            var bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
            bitmap = bitmap.getResizedBitmap(400)

            when (requestCode) {
                PICK_AVATAR_REQUEST -> {
                    profile_image.setImageWithGlide(bitmap)
                    account.profilePhotos.avatar = saveImageToInternalStorage(bitmap)
                    println(account)
                }
                PICK_BACKGROUND_REQUEST -> {
                    visit_background.setImageWithGlide(bitmap)
                    account.profilePhotos.background = saveImageToInternalStorage(bitmap)
                    println(account)
                }
            }

        }
    }

    companion object {
        private const val ACCOUNT_INFO = "account_info"

        fun create(account: Profile) =
            AvatarInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ACCOUNT_INFO, account)
                }
            }
    }
}
package com.example.visitus.ui.invite.createInvite

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Invite
import com.example.visitus.entity.InviteCategory
import com.example.visitus.presentation.invite.createInvite.ICreateInviteView
import com.example.visitus.presentation.invite.createInvite.CreateInviteImageUploadStepPresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.utils.argument
import com.example.visitus.utils.getResizedBitmap
import com.example.visitus.utils.setImageWithGlide
import kotlinx.android.synthetic.main.fragment_create_invite_step_with_photo_upload.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class CreateInviteUploadImageFragment : BaseFragment(), ICreateInviteView{
    override val layoutRes = R.layout.fragment_create_invite_step_with_photo_upload
    @InjectPresenter
    lateinit var presenter: CreateInviteImageUploadStepPresenter

    private val visit by argument<Invite>(VISIT_INFO, null)

    @ProvidePresenter
    fun providePresenter(): CreateInviteImageUploadStepPresenter =
        scope.getInstance(CreateInviteImageUploadStepPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideProgressBar()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        hideProgressBar()
    }
    private fun  initListeners(){
        visitImage.setOnClickListener {
            chooseVisitPhoto()
        }

        nextButton.setOnClickListener {
            showProgressBar()
            presenter.onNextClick(visit)
        }
        back_button.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            var bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
            bitmap = bitmap.getResizedBitmap(400)
            visitImage.setImageWithGlide(bitmap)
            visit.imageFilePath = saveImageToInternalStorage(bitmap)
        }
        }

    override fun updateRecyclerView(data: List<InviteCategory>) {

    }

    private fun showProgressBar() {
        pBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        pBar.visibility = View.GONE
    }


    private fun chooseVisitPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            1
        )
    }

    companion object {
        private const val VISIT_INFO = "visit_info"

        fun create(invite: Invite) =
            CreateInviteUploadImageFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(VISIT_INFO, invite)
                }
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

}
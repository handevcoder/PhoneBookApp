package hi.iwansyy.phonebookapp.view.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import hi.iwansyy.phonebookapp.databinding.FragmentContactBinding
import hi.iwansyy.phonebookapp.viewmodel.ContactState
import hi.iwansyy.phonebookapp.viewmodel.ContactViewModel
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class ContactFragment : Fragment() {
    private val requestPermissions = 111
    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private var filePath = ""
    private lateinit var binding: FragmentContactBinding
    private val requestImageCamera = 123
    private val requestImageGallery = 321
    private val contactViewModel by viewModels<ContactViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(layoutInflater, container, false).apply {
            ivContact.setOnClickListener { showOption() }
            btnCreate.setOnClickListener {
                contactViewModel.insertContact(
                    tilName.text.toString(),
                    tilPhone.text.toString(),
                    tilEmail.text.toString(),
                    tilCompany.text.toString(),
                    tilJob.text.toString(),
                    filePath
                )
            }

            contactViewModel.state.observe(viewLifecycleOwner) {
                when (it) {
                    is ContactState.Loading -> {
                        btnCreate.visibility = View.GONE
                    }
                    is ContactState.Create -> {
                        btnCreate.visibility = View.VISIBLE
                    }
                    is ContactState.Error -> {
                        btnCreate.visibility = View.VISIBLE
                    }
                }
            }
        }
        return binding.root
    }

    private fun showOption() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Pilih Gambar dari mana ?")
        builder.setItems(arrayOf("Camera", "Gallery")) { dialog, index ->
            dialog.dismiss()
            if (index == 0) captureImageFromCamera()
            else captureImageFromGallery()
        }
        builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun createImageFile(): File {
        deleteFileTemp()

        val timeStamp: String = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Date())
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("IMG_${timeStamp}", ".jpg", storageDir).apply {
            filePath = absolutePath
        }
    }

    private fun deleteFileTemp() {
        val file = File(filePath)
        if (file.exists() && file.delete()) {
            println("File dengan alamat ${file.absolutePath} berhasil dihapus")
        }
    }

    private fun captureImageFromGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, requestImageGallery)
    }

    private fun captureImageFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(), "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, requestImageCamera)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestImageCamera && resultCode == Activity.RESULT_OK) {
            Glide.with(this).load(filePath).into(binding.ivContact)
        } else if (requestCode == requestImageGallery && resultCode == Activity.RESULT_OK) {
            val image = try {
                createImageFile()
            } catch (exc: Exception) {
                null
            }

            image?.let {
                val bitmap = data?.data
                bitmap?.let { imageBitmap ->
                    val inputStream = requireActivity().contentResolver.openInputStream(imageBitmap)
                    val fileOutputStream = FileOutputStream(filePath)

                    inputStream?.let { input ->
                        try {
                            copyStream(input, fileOutputStream)
                        } catch (exc: java.lang.Exception) {
                            exc.printStackTrace()
                        }
                    }
                    fileOutputStream.close()
                    inputStream?.close()

                    Glide.with(this).load(filePath).into(binding.ivContact)

                }
            }

        } else {
            deleteFileTemp()
        }
    }

    private fun copyStream(input: InputStream, output: OutputStream) {
        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (input.read(buffer).also { bytesRead = it } != -1) {
            output.write(buffer, 0, bytesRead)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        deleteFileTemp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestPermissions && grantResults.size != permissions.size) {
            requestPermissions(permissions, requestPermissions)
        }
    }

    override fun onStart() {
        super.onStart()
        requestPermissions(permissions, requestPermissions)
    }

}
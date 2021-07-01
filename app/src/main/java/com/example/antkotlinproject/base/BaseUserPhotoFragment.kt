package com.example.antkotlinproject.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.reflect.KClass

@RuntimePermissions
abstract class BaseUserPhotoFragment : Fragment() {

    abstract fun showPhoto(file: File)
    abstract fun showPhoto1(file: Uri?)

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun pickPhotoFromGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        intent.type = "image/*"
        startActivityForResult(intent, RESULT_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RESULT_CAMERA -> {
                    val uri = getImageFromCameraUri(data, filename!!)
                    val uriFile = getNormalizedUri(uri = uri)
                    showPhoto1(uriFile)
                    val file = File(uriFile?.path)
                    file?.let { showPhoto(it) }

                    Log.d("Adasdasdasd", "adsadasdasdasd")

                }
                RESULT_GALLERY -> {
                    if (data != null && data.data != null) {
                        val fileName = getImagePathFromInputStreamUri(requireContext(), data.data!!)
                        val file = File(fileName)
                        showPhoto(file)
                    }
                }
            }
        }
    }

    private fun getImageFromCameraUri(data: Intent?, filename: String): Uri? {
        var isCamera = true
        if (data != null && data.data != null) {
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }

        return if (isCamera || data!!.data == null)
            activity?.let { it -> getCaptureImageOutputUri(it.applicationContext, filename) }
        else
            data.data
    }

    private fun getCaptureImageOutputUri(context: Context, fileName: String): Uri? {
        var outputFileUri: Uri? = null
        val getImage = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.path, "$fileName.jpeg"))
        }
        return outputFileUri
    }

    fun getNormalizedUri(uri: Uri?): Uri? {
        return if (uri != null && uri.toString().contains("content:"))
            activity?.let { it ->
                Uri.fromFile(
                    getPath(
                        it.applicationContext,
                        uri,
                        MediaStore.Images.Media.DATA
                    )
                )
            }
        else
            uri
    }


    private fun getPath(context: Context, uri: Uri, column: String): File? {
        val columns = arrayOf(column)
        val cursor = context.contentResolver.query(uri, columns, null, null, null) ?: return null
        val columnIndex = cursor.getColumnIndexOrThrow(column)
        cursor.moveToFirst()
        val path = cursor.getString(columnIndex)
        cursor.close()
        return File(path)
    }

    fun getImagePathFromInputStreamUri(context: Context, uri: Uri): String? {
        var inputStream: InputStream? = null
        var filePath: String? = null

        if (uri.authority != null) {
            try {
                inputStream = context.contentResolver.openInputStream(uri)
                val photoFile = createTemporalFileFrom(context, inputStream)
                filePath = photoFile!!.path
            } finally {
                try {
                    inputStream!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return filePath
    }

    @Throws(IOException::class)
    private fun createTemporalFileFrom(context: Context, inputStream: InputStream?): File? {
        var targetFile: File? = null

        if (inputStream != null) {
            var read: Int
            val buffer = ByteArray(8 * 1024)

            targetFile = createTemporalFile(context)
            val outputStream = FileOutputStream(targetFile)

            while (true) {
                read = inputStream.read(buffer)
                if (read == -1)
                    break
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()

            try {
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return targetFile
    }

    private fun createTemporalFile(
        context: Context,
        filePath: String = Calendar.getInstance().timeInMillis.toString()
    ): File {
        return File(context.externalCacheDir, "$filePath.jpg") // context needed
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val RESULT_CAMERA = 101
        private const val RESULT_GALLERY = 102
        private var filename: String? = null
    }
}

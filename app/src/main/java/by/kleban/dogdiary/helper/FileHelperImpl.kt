package by.kleban.dogdiary.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.net.URI
import java.util.*
import javax.inject.Inject

class FileHelperImpl @Inject constructor(@ApplicationContext val context: Context) : FileHelper {

    override suspend fun saveFileIntoAppsDir(uri: Uri, name: String): URI {
        val originalFileUri = URI(uri.toString())
        val originalFileUriAndroid = Uri.parse(originalFileUri.toString())
        val dataDir = ContextCompat.getDataDir(context) ?: throw java.lang.Exception()
        val newFile = File(dataDir.path, "${name}_${UUID.randomUUID()}")
        val outputStream = newFile.outputStream()
        val inputStream = context.contentResolver.openInputStream(originalFileUriAndroid) ?: throw java.lang.Exception()

        try {
            inputStream.copyTo(outputStream)
        } catch (e: Exception) {
            Timber.e(e)
        } finally {
            outputStream.close()
            inputStream.close()
        }
        return newFile.toURI()
    }

    override suspend fun createThumbnail(uri: URI, name: String): URI {
        val originalImgFile = File(uri)
        val bitmap = BitmapFactory.decodeFile(originalImgFile.path)
        val dataDir = ContextCompat.getDataDir(context) ?: throw java.lang.Exception()
        val thumbFile = File(dataDir.path, "${name}_${UUID.randomUUID()}")

        val ratio = 600f / minOf(bitmap.width, bitmap.height)
        val scaledWidth = (bitmap.width * ratio).toInt()
        val scaledHeight = (bitmap.height * ratio).toInt()
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)
        thumbFile.outputStream().use {
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, it)
        }
        return thumbFile.toURI()
    }

    override suspend fun deleteImages(imageUri: String) {
        val uri = URI(imageUri)
        File(uri).delete()
    }

}
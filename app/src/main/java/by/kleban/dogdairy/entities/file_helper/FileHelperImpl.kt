package by.kleban.dogdairy.entities.file_helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.net.URI
import java.util.*
import javax.inject.Inject

class FileHelperImpl @Inject constructor(@ApplicationContext val context: Context) : FileHelper {

    override suspend fun saveFileIntoAppsDir(uri: Uri, name: String): Pair<URI, URI> {

        val originalFileUri = URI(uri.toString())
        val originalFileUriAndroid = Uri.parse(originalFileUri.toString())
        val dataDir = ContextCompat.getDataDir(context) ?: throw java.lang.Exception()
        val newFile = File(dataDir.path, "${name}_${UUID.randomUUID()}")
        val newFileLittle = File(dataDir.path, "${name}_little_${UUID.randomUUID()}")
        val outputStream = newFile.outputStream()
        val inputStream = context.contentResolver.openInputStream(originalFileUriAndroid) ?: throw java.lang.Exception()
        val inputStreamLittle = context.contentResolver.openInputStream(originalFileUriAndroid) ?: throw java.lang.Exception()
        val outputStreamLittle = newFileLittle.outputStream()
        val bitmap = BitmapFactory.decodeStream(inputStreamLittle)

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamLittle)
            inputStream.copyTo(outputStream)
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) }
        } finally {
            outputStreamLittle.close()
            outputStream.close()
            inputStream.close()
            inputStreamLittle.close()
        }
        val newFileUri = newFile.toURI()
        val newLittleFileUri = newFileLittle.toURI()
        return newFileUri to newLittleFileUri
    }

    companion object {
        private const val TAG = "FileHelperImpl::class"
    }
}
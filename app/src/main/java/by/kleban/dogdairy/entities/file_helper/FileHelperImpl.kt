package by.kleban.dogdairy.entities.file_helper

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.net.URI
import java.util.*
import javax.inject.Inject

class FileHelperImpl @Inject constructor(@ApplicationContext val context: Context) : FileHelper {

    override suspend fun saveFileIntoAppsDir(uri: Uri, name: String): URI {
        val originalImgUri = URI(uri.toString())
        val originalImgUriAndroid = Uri.parse(originalImgUri.toString())
        val dataDir = ContextCompat.getDataDir(context) ?: throw java.lang.Exception()
        val newImgFile = File(dataDir.path, "${name}_${UUID.randomUUID()}")
        val outputStream = newImgFile.outputStream()
        val inputStream = context.contentResolver.openInputStream(originalImgUriAndroid) ?: throw java.lang.Exception()
        try {
            inputStream.copyTo(outputStream)
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) }
        } finally {
            outputStream.close()
            inputStream.close()
        }
        val newImgUri = newImgFile.toURI()
        return newImgUri
    }

    companion object {
        private const val TAG = "FileHelperImpl::class"
    }
}
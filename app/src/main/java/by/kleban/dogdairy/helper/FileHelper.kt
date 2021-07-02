package by.kleban.dogdairy.helper

import android.net.Uri
import java.net.URI


interface FileHelper {

    suspend fun saveFileIntoAppsDir(uri: Uri, name: String): URI

    suspend fun createThumbnail(uri: URI, name: String): URI

    suspend fun deleteImages(imageUri: String)
}
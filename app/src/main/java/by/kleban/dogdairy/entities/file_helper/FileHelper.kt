package by.kleban.dogdairy.entities.file_helper

import android.net.Uri
import java.net.URI


interface FileHelper {

    suspend fun saveFileIntoAppsDir(uri: Uri, name: String): URI
}
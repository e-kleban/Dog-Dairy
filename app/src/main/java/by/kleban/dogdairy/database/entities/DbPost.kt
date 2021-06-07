package by.kleban.dogdairy.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_post")
data class DbPost(
    val dogCreatorId: Int,
    val postImage: String,
    val postDescription: String,

    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

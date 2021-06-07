package by.kleban.dogdairy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbPost(
    val dogCreatorId: Int,
    val postImage: String,
    val postDescription: String,
    @PrimaryKey(autoGenerate = true)
    var postId: Int
) {

}

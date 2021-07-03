package by.kleban.dogdiary.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_post")
data class DbPost(
    val dogCreatorId: Long,
    val image: String,
    val thumbnail: String,
    val description: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}

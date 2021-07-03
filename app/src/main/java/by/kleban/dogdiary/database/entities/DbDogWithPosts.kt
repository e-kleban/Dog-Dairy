package by.kleban.dogdiary.database.entities

import androidx.room.Embedded
import androidx.room.Relation


data class DbDogWithPosts(

    @Embedded val dbDog: DbDog,
    @Relation(
        parentColumn = "id",
        entityColumn = "dogCreatorId"
    )
    val dbPosts: List<DbPost>
)

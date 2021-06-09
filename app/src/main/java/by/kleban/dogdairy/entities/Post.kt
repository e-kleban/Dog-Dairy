package by.kleban.dogdairy.entities

import java.io.Serializable


data class Post(
    val dogCreatorId: Long,
    val postImage: String,
    val postDescription: String,
) : Serializable
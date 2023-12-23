package com.example.mobileapp.api.model

import android.graphics.Bitmap
import com.example.mobileapp.database.entities.Story
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class StoryRemote(
    val id: Int? = null,
    val title: String,
    val description: String,
    val cover: String,
    val postdate: Long? = Date().time,
    val userId: Int
)

fun StoryRemote.toStory(): Story = Story(
    id,
    title,
    description,
    RemoteConverters.toBitmap(cover),
    postdate,
    userId
)

fun Story.toStoryRemote(): StoryRemote = StoryRemote(
    id,
    title,
    description,
    RemoteConverters.fromBitmap(cover),
    postdate,
    userId
)


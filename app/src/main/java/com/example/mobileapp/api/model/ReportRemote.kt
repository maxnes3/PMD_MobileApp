package com.example.mobileapp.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ReportRemote(
    val dateFrom: Long,
    val dateTo: Long,
    val postCount: Int,
    val mostPostAuthor: UserRemote,
    val mostPostCount: Int,
    val listPostAuthor: List<StoryRemote>
)

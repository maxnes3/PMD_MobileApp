package com.example.mobileapp.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ReportRemote(
    val dateFrom: Long,
    val dateTo: Long,
    val postCount: Int,
    val mostPostAuthor: String,
    val mostPostCount: Int
)

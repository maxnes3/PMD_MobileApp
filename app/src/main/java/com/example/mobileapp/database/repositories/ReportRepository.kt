package com.example.mobileapp.database.repositories

import com.example.mobileapp.api.model.ReportRemote

interface ReportRepository {
    suspend fun createReport(dateFrom: Long, dateTo: Long): ReportRemote
}
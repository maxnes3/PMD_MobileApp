package com.example.mobileapp.api.repository

import com.example.mobileapp.api.ServerService
import com.example.mobileapp.api.model.ReportRemote
import com.example.mobileapp.database.repositories.ReportRepository

class RestReportRepository(private var service: ServerService): ReportRepository {
    override suspend fun createReport(dateFrom: Long, dateTo: Long): ReportRemote = service.createReport(dateFrom, dateTo)
}
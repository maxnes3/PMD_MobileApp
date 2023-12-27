package com.example.mobileapp.database.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.api.model.ReportRemote
import com.example.mobileapp.database.repositories.ReportRepository
import kotlinx.coroutines.launch

class ReportViewModel(private val reportRepository: ReportRepository): ViewModel() {
    private var _report = mutableStateOf<ReportRemote?>(null)
    val report: MutableState<ReportRemote?> get() = _report

    fun createReport(dateFrom: Long, dateTo: Long) = viewModelScope.launch {
        _report.value = reportRepository.createReport(dateFrom, dateTo)
    }

    fun clearReport(){
        _report.value = null
    }
}
package com.example.mobileapp.database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mobileapp.database.entities.Mail
import com.example.mobileapp.database.repositories.MailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MailViewModel(private val mailRepository: MailRepository): ViewModel() {
    val getAllMails: Flow<PagingData<Mail>> = mailRepository.getAllMails().cachedIn(viewModelScope)

    suspend fun getMail(id: Int):  Mail? = mailRepository.getMail(id)

    fun insertMail(mail: Mail) = viewModelScope.launch {
        mailRepository.insertMail(mail)
    }

    fun updateMail(mail: Mail) = viewModelScope.launch {
        mailRepository.updateMail(mail)
    }

    fun deleteMail(mail: Mail) = viewModelScope.launch {
        mailRepository.deleteMail(mail)
    }
}
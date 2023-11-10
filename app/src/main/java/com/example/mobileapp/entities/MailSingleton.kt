package com.example.mobileapp.entities

class MailSingleton {
    companion object {
        val mailList: MutableList<Mail> = mutableListOf()
    }

    fun addMail(mail: Mail) {
        mailList.add(mail)
    }

    fun getMailList(): List<Mail> {
        return mailList.toList()
    }
}
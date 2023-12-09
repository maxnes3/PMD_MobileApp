package com.example.mobileapp.database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.GlobalUser
import com.example.mobileapp.database.entities.User
import com.example.mobileapp.database.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    val getAllUsers = userRepository.getAllUsers()

    fun getUser(id: Int): Flow<User?> = userRepository.getUser(id)

    fun updateUser(user: User) = viewModelScope.launch {
        if (user.login.isEmpty()){
            return@launch
        }

        if (user.email.isEmpty() || !isValidEmail(user.email)){
            return@launch
        }

        if (user.password.isEmpty()){
            return@launch
        }
        userRepository.updateUser(user)
        GlobalUser.getInstance().setUser(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        userRepository.deleteUser(user)
    }

    fun regUser(user: User) = viewModelScope.launch {
        val globalUser = userRepository.getUserByLogin(user.login)
        globalUser?.let {
            return@launch
        } ?: run {
            if(user.password.isEmpty()){
                return@launch
            }

            if(user.email.isEmpty() || !isValidEmail(user.email)){
                return@launch
            }
            userRepository.insertUser(user)
            GlobalUser.getInstance().setUser(userRepository.getUserByLogin(user.login))
        }
    }

    fun authUser(user: User) = viewModelScope.launch {
        val globalUser = userRepository.getUserByLogin(user.login)
        globalUser?.let {
            if (user.password.isNotEmpty() && user.password == globalUser.password){
                GlobalUser.getInstance().setUser(globalUser)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
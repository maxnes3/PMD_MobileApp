package com.example.mobileapp.database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.GlobalUser
import com.example.mobileapp.api.model.UserRemoteSignIn
import com.example.mobileapp.api.model.toUserRemote
import com.example.mobileapp.database.entities.User
import com.example.mobileapp.database.repositories.OfflineUserRepository
import com.example.mobileapp.database.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): CustomViewModel() {
    //val getAllUsers = userRepository.getAllUsers()

    suspend fun getUser(id: Int): User? = userRepository.getUser(id)

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
        runInScope(
            actionSuccess = {
                userRepository.updateUser(user)
                GlobalUser.getInstance().setUser(user)
            }
        )
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        userRepository.deleteUser(user)
    }

    fun regUser(user: User) = viewModelScope.launch {
        if(user.password.isEmpty()){
            return@launch
        }

        if(user.email.isEmpty() || !isValidEmail(user.email)){
            return@launch
        }
        runInScope(
            actionSuccess = {
                userRepository.insertUser(user)
                GlobalUser.getInstance().setUser(userRepository.getUserByLogin(
                    UserRemoteSignIn(user.login, user.password)))
            }
        )
        /*userRepository.insertUser(user)
        GlobalUser.getInstance().setUser(userRepository.getUserByLogin(
            UserRemoteSignIn(user.login, user.password)))*/
    }

    fun authUser(user: User) = viewModelScope.launch {
        runInScope(
            actionSuccess = {
                val globalUser = userRepository.getUserByLogin(UserRemoteSignIn(user.login, user.password))
                globalUser?.let {
                    if (user.password.isNotEmpty() && user.password == globalUser.password){
                        GlobalUser.getInstance().setUser(globalUser)
                    }
                }
            },
            actionError = {
                GlobalUser.getInstance().setUser(null)
            }
        )
        /*val globalUser = userRepository.getUserByLogin(UserRemoteSignIn(user.login, user.password))
        globalUser?.let {
            if (user.password.isNotEmpty() && user.password == globalUser.password){
                GlobalUser.getInstance().setUser(globalUser)
            }
        }*/
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
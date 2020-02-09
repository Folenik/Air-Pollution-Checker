package com.example.airapi.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.airapi.DAO.Repository

class LoginUserViewModelFactory(
    private val database: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginUserViewModel::class.java)) {
            return LoginUserViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
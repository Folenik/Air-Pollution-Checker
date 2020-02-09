package com.example.airapi.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.airapi.DAO.Repository

class AddUserViewModelFactory(
    private val database: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddUserViewModel::class.java)) {
            return AddUserViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
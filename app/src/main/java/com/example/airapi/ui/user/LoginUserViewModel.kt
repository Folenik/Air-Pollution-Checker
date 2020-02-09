package com.example.airapi.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airapi.DAO.Repository

class
LoginUserViewModel(val database: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is login user Fragment"
    }
    val text: LiveData<String> = _text

    fun isValidAccount(email: String, password: String): Boolean {
        return database.isValidAccount(email, password)
    }

    fun setCurrentUserLoggedIn(email: String) {
        database.setUserLoggedIn(email)
    }

    fun setCurrentUserLoggedOut(): Int {
        return database.setUserLoggedOut()
    }

    fun checkIfUserLoggedIn(): Boolean {
        return database.checkIfUserLoggedIn()
    }
}
package com.example.airapi.ui.station

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airapi.DAO.Repository

class StationViewModel(private val database: Repository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is station fragment"
    }
    val text: LiveData<String> = _text

    fun setFavouriteStation(stacja_id: Int, user_id: Int) {
        database.setFavouriteStation(stacja_id, user_id)
    }

    fun getCurrentUserId(): Int {
        return database.getCurrentUserId()
    }

    fun checkIfUserLoggedIn(): Boolean {
        return database.checkIfUserLoggedIn()
    }
}
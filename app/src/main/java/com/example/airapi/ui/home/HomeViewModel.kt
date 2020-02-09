package com.example.airapi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airapi.DAO.Repository

class HomeViewModel(private val database: Repository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is station fragment"
    }
    val text: LiveData<String> = _text

    fun getCurrentUserId(): Int {
        return database.getCurrentUserId()
    }

    fun getCurrentUserFavorites(): List<Int> {
        return database.getCurrentUserFavorites()
    }

    fun deleteFavouriteStation(stacja_id: Int, user_id: Int) {
        database.deleteFavouriteStation(stacja_id, user_id)
    }
}
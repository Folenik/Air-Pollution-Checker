package com.example.airapi.ui.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.airapi.DAO.Repository

class StationViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StationViewModel::class.java)) {
            return StationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
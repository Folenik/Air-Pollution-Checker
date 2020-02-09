package com.example.airapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favorites")
data class UserFavorites(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var id_stacja: Int? = null,
    var id_user: Int? = null
)
package com.example.airapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var userLogged: Int
)


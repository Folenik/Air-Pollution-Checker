package com.example.airapi.DAO

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.airapi.models.User
import com.example.airapi.models.UserFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Repository(fragment: Fragment) {

    private val modelDao: ModelDao
    private val allUsers: LiveData<List<User>>
    private val allFavorites: LiveData<List<UserFavorites>>

    init {
        val database = AirDatabase.getInstance(fragment.requireContext())
        modelDao = database.dao()
        allUsers = modelDao.getUsers()
        allFavorites = modelDao.getFavorites()
    }

    fun addUser(user: User) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                modelDao.addUser(user)
            }
        }
    }

    fun getCurrentUserId(): Int {
        var user: User? = null
        runBlocking {
            this.launch(Dispatchers.IO) {
                user = modelDao.getCurrentUserId()
            }
        }
        return user!!.id!!
    }

    fun isValidAccount(email: String, password: String): Boolean {
        var user: User? = null
        runBlocking {
            this.launch(Dispatchers.IO) {
                user = modelDao.getCurrentUser(email)
            }
        }
        return user?.password == password
    }

    fun setUserLoggedIn(email: String): Int {
        runBlocking {
            this.launch(Dispatchers.IO) {
                modelDao.setCurrentUserLoggedIn(email)
            }
        }
        return 1
    }

    fun setUserLoggedOut(): Int {
        runBlocking {
            this.launch(Dispatchers.IO) {
                modelDao.setCurrentUserLoggedOut()
            }
        }
        return 0
    }

    fun setFavouriteStation(id_stacja: Int, id_user: Int) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                modelDao.insertFavouriteStation(id_stacja, id_user)
            }
        }
    }

    fun getCurrentUserFavorites(): List<Int> {
        var currentUserFavorites: List<Int>? = null
        runBlocking {
            this.launch(Dispatchers.IO) {
                currentUserFavorites = modelDao.getCurrentUserFavorites()
            }
        }
        return currentUserFavorites!!
    }

    fun deleteFavouriteStation(id_stacja: Int, id_user: Int) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                modelDao.deleteFavouriteStation(id_stacja, id_user)
            }
        }
    }

    fun checkIfUserLoggedIn(): Boolean {
        var isLogged: Boolean? = null
        runBlocking {
            this.launch(Dispatchers.IO) {
                isLogged = modelDao.checkIfUserLogged()
            }
        }
        return isLogged!!
    }

    fun checkIfUserExists(email: String): Boolean {
        var exists: Boolean? = null
        runBlocking {
            this.launch(Dispatchers.IO) {
                exists = modelDao.checkIfUserExists(email)
            }
        }
        return exists!!
    }
}
package com.example.airapi.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.airapi.models.User
import com.example.airapi.models.UserFavorites

@Dao
interface ModelDao {
    @Insert
    suspend fun addUser(user: User)

    @Insert
    suspend fun addFavorite(favorites: UserFavorites)

    @Query("SELECT * FROM user_table")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT SUM(id) FROM user_table")
    fun getUsersCount(): LiveData<Int>

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun getUser(id: Int): User

    @Query("SELECT * FROM user_table WHERE email = :email")
    fun getCurrentUser(email: String): User

    @Query("UPDATE user_table SET userLogged = 1 WHERE user_table.email = :email")
    fun setCurrentUserLoggedIn(email: String): Int

    @Query("UPDATE user_table SET userLogged = 0 WHERE user_table.userLogged = 1")
    fun setCurrentUserLoggedOut(): Int

    @Query("INSERT INTO user_favorites(id_stacja, id_user) VALUES (:id_stacja, :id_user)")
    fun insertFavouriteStation(id_stacja: Int, id_user: Int)

    @Query("SELECT * FROM user_table WHERE userLogged = 1")
    fun getCurrentUserId(): User

    @Query("SELECT * FROM user_favorites")
    fun getFavorites(): LiveData<List<UserFavorites>>

    @Query("SELECT * FROM user_favorites")
    fun getFavorites2(): UserFavorites

    @Query("SELECT user_favorites.id_stacja FROM user_favorites, user_table WHERE user_favorites.id_user = user_table.id AND user_table.userLogged = 1")
    fun getCurrentUserFavorites(): List<Int>

    @Query("DELETE FROM user_favorites WHERE id_stacja = :id_stacja AND id_user = :id_user")
    fun deleteFavouriteStation(id_stacja: Int, id_user: Int)

    @Query("SELECT * FROM user_table WHERE userLogged = 1")
    fun checkIfUserLogged(): Boolean

    @Query("SELECT * FROM user_table WHERE email = :email")
    fun checkIfUserExists(email: String): Boolean

}
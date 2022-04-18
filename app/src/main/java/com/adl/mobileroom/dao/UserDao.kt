package com.adl.mobileroom.dao

import androidx.room.*
import com.adl.mobileroom.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Query("SELECT * FROM user ")
    fun getUser(): List<User>

    @Update
    fun updateUser(user:User)
    @Update
    fun updateUsers(user:List<User>)

    @Delete
    fun deleteUser(user:User)




}
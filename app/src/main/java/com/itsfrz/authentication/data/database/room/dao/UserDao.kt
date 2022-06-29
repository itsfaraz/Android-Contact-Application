package com.itsfrz.authentication.model.database.room.dao

import androidx.room.*
import com.itsfrz.authentication.data.entities.UserModel

@Dao
interface UserDao {


    @Insert
    suspend fun insertUser(userModel: UserModel)

    @Update
    suspend fun updateUser(userModel: UserModel)

    @Delete
    suspend fun deleteUser(userModel: UserModel)

    @Query("SELECT * FROM UserModel Where username = :username and emailId =:email")
    suspend fun isUserExists(username: String,email:String) : List<UserModel>

    @Query("SELECT * FROM UserModel Where username = :username and password =:password")
    suspend fun loginUser(username: String,password:String) : UserModel?


}
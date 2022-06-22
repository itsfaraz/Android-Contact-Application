package com.itsfrz.authentication.database.room.dao

import androidx.room.*
import com.itsfrz.authentication.database.room.model.UserModel

@Dao
interface UserDao {


    @Insert
    suspend fun insertUser(userModel: UserModel) : Long

//    @Query("UPDATE UserModel WHERE username=:username and emailId=:emailId")
//    suspend fun updateUser(newUserModel: UserModel,username : String,emailId : String)

//    @Delete
//    suspend fun deleteUser(userModel: UserModel)
//
//    @Query("SELECT * FROM UserModel WHERE username=:username and password=:password")
//    suspend fun getUserInfo(username: String,password : String)


    @Query("SELECT * FROM UserModel Where username = :username and emailId =:email")
    suspend fun isUserExists(username: String,email:String) : List<UserModel>

    @Query("SELECT * FROM UserModel Where username = :username and password =:password")
    suspend fun loginUser(username: String,password:String) : UserModel?


}
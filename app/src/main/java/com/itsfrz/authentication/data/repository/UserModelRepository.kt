package com.itsfrz.authentication.data.repository

import com.itsfrz.authentication.model.database.room.dao.UserDao
import com.itsfrz.authentication.data.entities.UserModel

class UserModelRepository(private val userDao: UserDao) {

    suspend fun insert(userModel: UserModel) = userDao.insertUser(userModel)

    suspend fun update(userModel: UserModel) = userDao.updateUser(userModel)

    suspend fun delete(userModel: UserModel) = userDao.deleteUser(userModel)

    // LiveData
    suspend fun isUserExists(username : String,email : String) : List<UserModel> = userDao.isUserExists(username, email)

    suspend fun loginUser(username: String,password : String) : UserModel? = userDao.loginUser(username, password)

}
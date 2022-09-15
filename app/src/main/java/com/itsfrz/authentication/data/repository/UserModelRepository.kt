package com.itsfrz.authentication.data.repository

import com.itsfrz.authentication.model.database.room.dao.UserDao
import com.itsfrz.authentication.data.entities.UserModel
import com.itsfrz.authentication.data.utils.ContactLog

class UserModelRepository(private val userDao: UserDao) {

    suspend fun insert(userModel: UserModel) = userDao.insertUser(userModel)

    suspend fun update(userModel: UserModel) = userDao.updateUser(userModel)

    suspend fun delete(userModel: UserModel) = userDao.deleteUser(userModel)

    // LiveData
    suspend fun isUserExists(username : String,email : String) : List<UserModel>?{
        try {
           return userDao.isUserExists(username, email)
        }catch (e : Exception){
            ContactLog.debugLog("USER_MODEL","isUserExists :- ${e.message}")
            e.printStackTrace()
        }
        return null
    }

    suspend fun loginUser(username: String,password : String) : UserModel? = userDao.loginUser(username, password)

}
package com.itsfrz.authentication.ui.utils

class RegexValidation {

    private val usernamePattern = Regex("^[a-zA-Z0-9]{3,20}\$")
    private val emailPattern = Regex("^[\\w\\.]{3,20}@[\\w]{3,15}(\\.[a-z]{2,8})(\\.[a-z]{2,8})?\$")
    private val passwordPattern = Regex("^[0-9]{5,40}\$")
    private val mobileNumberPattern = Regex("")

    fun validateUsername(username : String) : Boolean = usernamePattern.matches(username)
    fun validatePassword(password : String) : Boolean = passwordPattern.matches(password) ?: false
    fun validateEmail(email : String) : Boolean = emailPattern.matches(email) ?: false
    fun validateMobileNumber(mobileNumber : String) : Boolean = true

}
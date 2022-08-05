package com.itsfrz.authentication.ui.views

sealed class Screen(val route : String){
    object  HomeScreen : Screen("home_screen")
    object  AuthenticationScreen : Screen("authentication_screen")
    object  LoginScreen : Screen("login_screen")
    object  SignupScreen : Screen("signup_screen")
    object  PermissionScreen : Screen("permission_screen")
    object  UserInfoScreen : Screen("user_info_screen")
    object  ImportContactScreen : Screen("import_contact_screen")

}
package com.itsfrz.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.itsfrz.authentication.ui.utils.Helper


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        navigationHandler(navController)


    }


    private fun navigationHandler(navController: NavController) {
        navController.navigate(
            resId = R.id.permissionFragment,
            args = null,
            navOptions = Helper.navOptions
        )

    }

}
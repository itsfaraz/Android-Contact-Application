package com.itsfrz.authentication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.repository.UserModelRepository
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.ui.utils.Helper
import com.itsfrz.authentication.ui.utils.RegexValidation
import com.itsfrz.authentication.ui.viewmodel.LoginViewModel
import com.itsfrz.authentication.ui.viewmodelfactory.LoginViewModelFactory
import com.itsfrz.authentication.ui.views.compose.components.InputField
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.utils.Loader
import kotlin.math.log

class LoginFragment : Fragment() {


    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val userPreferenceRepository =  UserPreferenceRepository(requireContext())
        val regexValidation: RegexValidation = RegexValidation()
        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
        val userModelRepository: UserModelRepository = UserModelRepository(userDao)
        val loginViewModelProviderFactory = LoginViewModelFactory(
            userPreferenceRepository,
            regexValidation,
            userModelRepository
        )
        loginViewModel = ViewModelProvider(
            this,
            loginViewModelProviderFactory
        ).get(LoginViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val username = loginViewModel.username.value
                val password = loginViewModel.password.value
                val hasUsernameError = loginViewModel.hasUsernameError.value
                val hasPasswordError = loginViewModel.hasPasswordError.value
                val usernameErrorMessage = loginViewModel.usernameErrorMessage.value
                val passswordErrorMessage = loginViewModel.passwordErrorMessage.value
                val isProgressBar = loginViewModel.isProgressBar.value
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(60.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.contact_logo),
                            contentDescription = "Application Logo",
                            modifier = Modifier.size(60.dp),
                            tint = Blue100
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        InputField(
                            modifier = Modifier.padding(horizontal = 40.dp),
                            placeHolder = "Username ...",
                            label = "username",
                            hasError = hasUsernameError,
                            hasTrailingIcon = true,
                            trailingIcon = R.drawable.ui_login_icon,
                            errorMessage = usernameErrorMessage,
                            inputText = username,
                            generatedText = { loginViewModel.setUserName(it) }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        InputField(
                            modifier = Modifier.padding(horizontal = 40.dp),
                            placeHolder = "Password ...",
                            label = "password",
                            hasError = hasPasswordError,
                            hasTrailingIcon = true,
                            trailingIcon = R.drawable.ui_password_icon,
                            errorMessage = passswordErrorMessage,
                            inputText = password,
                            generatedText = { loginViewModel.setPassword(it) },
                            keyboardType = KeyboardType.NumberPassword
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp),
                            onClick = {
                                val response = loginViewModel.login()
                                Log.d("LoginFrag", "onCreateView: $response")
                                if (response) {
                                    findNavController().navigate(
                                        resId = R.id.contactFragment,
                                        args = null,
                                        navOptions = Helper.navOptions
                                    )
                                }else{
                                    Toast.makeText(requireContext(), "Invalid Credentials, Try again or Signup", Toast.LENGTH_SHORT).show()
                                }                            },
                            colors = ButtonDefaults.buttonColors(Blue100)
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "Log in",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        PrivacyPolicy()
                        Spacer(modifier = Modifier.height(50.dp))
                        SignUpText()
                    }

                    if (isProgressBar){
                        Loader(loaderMessage = "Wait Login ...")
                    }


                }
            }
        }
    }
}

@Composable
fun SignUpText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Don't have an account ? ")
        Text(
            modifier = Modifier
                .clickable {},
            color = Blue100,
            text = "Sign Up",
            fontWeight = FontWeight.SemiBold
        )
    }
}

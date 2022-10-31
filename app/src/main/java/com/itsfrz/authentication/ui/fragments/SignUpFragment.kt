package com.itsfrz.authentication.ui.fragments

import android.os.Bundle
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.repository.UserModelRepository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.ui.utils.Helper
import com.itsfrz.authentication.ui.utils.RegexValidation
import com.itsfrz.authentication.ui.viewmodel.SignUpViewModel
import com.itsfrz.authentication.ui.viewmodel.factory.SignUpViewModelFactory
import com.itsfrz.authentication.ui.compose.components.InputField
import com.itsfrz.authentication.ui.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.compose.utils.Loader

class SignUpFragment : Fragment() {

    private lateinit var signUpViewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
        val signUpViewModelFactory = SignUpViewModelFactory(
            RegexValidation(),
            UserModelRepository(userDao)
        )
        signUpViewModel = ViewModelProvider(this, signUpViewModelFactory)
            .get(SignUpViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val username = signUpViewModel.username.value
                val password = signUpViewModel.password.value
                val email = signUpViewModel.email.value

                val hasUsernameError = signUpViewModel.hasUsernameError.value
                val hasPasswordError = signUpViewModel.hasPasswordError.value
                val hasEmailError = signUpViewModel.hasEmailError.value

                val usernameErrorMessage = signUpViewModel.usernameErrorMessage.value
                val passwordErrorMessage = signUpViewModel.passwordErrorMessage.value
                val emailErrorMessage = signUpViewModel.emailErrorMessage.value
                val isProgressBar = signUpViewModel.isProgressBar.value




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
                            generatedText = { signUpViewModel.setUsername(it) }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        InputField(
                            modifier = Modifier.padding(horizontal = 40.dp),
                            placeHolder = "Email ...",
                            label = "email",
                            hasError = hasEmailError,
                            hasTrailingIcon = true,
                            trailingIcon = R.drawable.ui_login_icon,
                            errorMessage = emailErrorMessage,
                            inputText = email,
                            generatedText = { signUpViewModel.setEmail(it) },
                            keyboardType = KeyboardType.Email
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        InputField(
                            modifier = Modifier.padding(horizontal = 40.dp),
                            placeHolder = "Password ...",
                            label = "password",
                            hasError = hasPasswordError,
                            hasTrailingIcon = true,
                            trailingIcon = R.drawable.ui_login_icon,
                            errorMessage = passwordErrorMessage,
                            inputText = password,
                            generatedText = { signUpViewModel.setPassword(it) },
                            keyboardType = KeyboardType.NumberPassword
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp),
                            onClick = {
                                val signUpResponse = signUpViewModel.signup()
                                if (signUpResponse) {
                                    findNavController().navigate(
                                        resId = R.id.loginFragment,
                                        args = null,
                                        navOptions = Helper.navOptions
                                    )
                                }else{
                                    Toast.makeText(requireContext(), "Username Already Exists :(", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Blue100)
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "Sign Up",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        PrivacyPolicy()
                        Spacer(modifier = Modifier.height(50.dp))
                        LoginText()
                    }

                    if (isProgressBar){
                        Loader(loaderMessage = "Registering ...")
                    }
                }


            }
        }
    }
}

@Composable
fun LoginText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Already have an account ? ")
        Text(
            modifier = Modifier
                .clickable {},
            color = Blue100,
            text = "Log in",
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PrivacyPolicy() {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.DarkGray)) {
                append("By signing up you accept then")
            }
            withStyle(style = SpanStyle(color = Blue100)) {
                append(" Terms of Service")
            }
            withStyle(style = SpanStyle(color = Color.DarkGray)) {
                append(" and")
            }
            withStyle(style = SpanStyle(color = Blue100)) {
                append("\nPrivacy Policy")
            }
        },
        lineHeight = 25.sp,
        textAlign = TextAlign.Center
    )
}
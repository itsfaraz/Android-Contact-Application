package com.itsfrz.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.ui.views.compose.components.AddUpdateField
import com.itsfrz.authentication.ui.views.compose.components.ConnectExtraLayout
import com.itsfrz.authentication.ui.views.compose.components.UserProfile
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue200

class AddContactFragment : Fragment() {

    private lateinit var operationType: String
    private var contactModel: ContactModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        operationType = arguments?.getString("operationType") ?: ""
        if (operationType == "update")
            contactModel = arguments?.getParcelable("contact")


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Blue100),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                   item{
                       UserProfile(userImage = "") {

                       }
                       Spacer(modifier = Modifier.height(10.dp))
                       Text(
                           modifier = Modifier.fillMaxWidth(75F),
                           text = "Faraz Sheikh",
                           textAlign = TextAlign.Center,
                           color = Color.White,
                           fontSize = 18.sp,
                           fontWeight = FontWeight.Bold

                       )
                       Spacer(modifier = Modifier.height(30.dp))
                   }
                   item{
                       Box(
                           modifier = Modifier
                               .fillMaxWidth()
                       ) {
                           ConnectExtraLayout({})
                           Card(
                               modifier = Modifier
                                   .padding(top = 110.dp)
                                   .fillMaxSize(),
                               shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
                               backgroundColor = Blue100,
                               elevation = 15.dp
                           ) {

                               Column(
                                   modifier = Modifier
                                       .fillMaxSize(),
                                   verticalArrangement = Arrangement.Top,
                                   horizontalAlignment = Alignment.CenterHorizontally
                               ) {

                                   Spacer(modifier = Modifier.height(20.dp))
                                   AddUpdateField(
                                       value = "",
                                       onValueChange = {},
                                       labelText = "Contact Name"
                                   )
                                   Spacer(modifier = Modifier.height(20.dp))
                                   AddUpdateField(
                                       value = "",
                                       onValueChange = {},
                                       labelText = "Contact Number",
                                       keyBoardOption = KeyboardOptions(
                                           keyboardType = KeyboardType.Number,
                                           imeAction = ImeAction.Done
                                       )
                                   )
                                   Spacer(modifier = Modifier.height(20.dp))
                                   AddUpdateField(
                                       value = "",
                                       onValueChange = {},
                                       labelText = "Contact Address",
                                       keyBoardOption = KeyboardOptions(
                                           keyboardType = KeyboardType.Text,
                                           imeAction = ImeAction.Done
                                       )
                                   )
                                   Spacer(modifier = Modifier.height(20.dp))
                                   AddUpdateField(
                                       value = "",
                                       onValueChange = {},
                                       labelText = "Contact Email Address",
                                       keyBoardOption = KeyboardOptions(
                                           keyboardType = KeyboardType.Email,
                                           imeAction = ImeAction.Done
                                       )
                                   )
                                   Spacer(modifier = Modifier.height(20.dp))
                                   Button(
                                       modifier = Modifier
                                           .fillMaxWidth(.75F),
                                       onClick = {

                                       },
                                       shape = RoundedCornerShape(12.dp),
                                       colors = ButtonDefaults.buttonColors(
                                           backgroundColor = Blue200
                                       )
                                   ) {
                                       Text(
                                           modifier = Modifier.fillMaxWidth(),
                                           text = "Save",
                                           fontSize = 14.sp,
                                           fontWeight = FontWeight.SemiBold,
                                           textAlign = TextAlign.Center,
                                           color = Color.White
                                       )
                                   }
                                   Spacer(modifier = Modifier.height(60.dp))

                               }


                           }
                       }
                   }

                    Toast.makeText(
                        requireContext(),
                        "From Contact Import :: Operation Type ${operationType}${contactModel?.toString() ?: ""}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
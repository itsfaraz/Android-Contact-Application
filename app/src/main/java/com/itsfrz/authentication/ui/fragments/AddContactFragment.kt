package com.itsfrz.authentication.ui.fragments

import android.content.Intent
import android.net.Uri
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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.entities.UserPreferences
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.ui.Permission
import com.itsfrz.authentication.ui.compose.components.AddUpdateField
import com.itsfrz.authentication.ui.compose.components.ConnectExtraLayout
import com.itsfrz.authentication.ui.compose.components.UserProfile
import com.itsfrz.authentication.ui.compose.enums.ConnectType
import com.itsfrz.authentication.ui.compose.enums.ContactOperation
import com.itsfrz.authentication.ui.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.compose.ui.theme.Blue200
import com.itsfrz.authentication.ui.utils.Helper
import com.itsfrz.authentication.ui.viewmodel.AddContactViewModel
import com.itsfrz.authentication.ui.viewmodel.factory.AddContactViewModelFactory
import java.util.jar.Manifest


class AddContactFragment : Fragment() {

    private var contactModel: ContactModel? = null
    private lateinit var operation: String
    private lateinit var viewModel: AddContactViewModel
    private var userPreferences: UserPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contactDao = AppDatabase.getDatabase(requireContext()).contactDao()
        val contactModelRepository = ContactModelRepository(contactDao)
        val contactProviderRepository = ContactProviderRepository(requireContext())
        val contactModelFactory =
            AddContactViewModelFactory(contactModelRepository, contactProviderRepository)
        viewModel = ViewModelProvider(this, contactModelFactory)[AddContactViewModel::class.java]


        operation = arguments?.getString("operation") ?: ""
        userPreferences = arguments?.getParcelable<UserPreferences>(Helper.USER_PREF)
        if (operation == ContactOperation.CONTACT_UPDATE.name || operation == ContactOperation.CONTACT_IMPORT_UPDATE.name) {
            contactModel = arguments?.getParcelable("contact")
            contactModel?.let {
                viewModel.updateUpcomingInfo(
                    contactPhoto = it.contactImage,
                    contactName = it.contactName,
                    contactNumber = it.contactNumber,
                    contactAddress = it.contactAddress,
                    contactEmail = it.contactEmailId
                )
            }
        }
        viewModel.setOperationType(operation)
        viewModel.setUsername(userPreferences?.username ?: "")
        Permission()
            .requestRunTimePermission(requireActivity(), arrayOf(android.Manifest.permission.WRITE_CONTACTS),Helper.WRITE_CONTACT_RQ)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                val contactName = viewModel.contactName.value
                val contactImage = viewModel.contactImage.value
                val contactEmail = viewModel.contactEmail.value
                val contactAddress = viewModel.contactAddress.value
                val contactNumber = viewModel.contactNumber.value
                val operationType = viewModel.operationType.value

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Blue100),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    item {
                        UserProfile(userImage = contactImage) {

                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(75F),
                            text = "$contactName",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold

                        )
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            if (!(operation == ContactOperation.CONTACT_ADD.name || operationType == ContactOperation.CONTACT_IMPORT_ADD.name)) {
                                ConnectExtraLayout(connectClick = { type: ConnectType ->
                                    when (type) {
                                        ConnectType.CALL -> {
                                            val intent = Intent(Intent.ACTION_DIAL)
                                            intent.data =
                                                Uri.parse("tel:$contactNumber")
                                            context.startActivity(intent)
                                        }
                                        ConnectType.VIDEO -> {
                                            Toast.makeText(
                                                requireContext(),
                                                "Video Call Not Available ¯\\_(ツ)_/¯",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        ConnectType.MESSAGE -> {
                                            val smsIntent = Intent(Intent.ACTION_VIEW)
                                            smsIntent.type = "vnd.android-dir/mms-sms"
                                            smsIntent.putExtra("address", contactNumber)
                                            smsIntent.putExtra("sms_body", "")
                                            startActivity(smsIntent)
                                        }
                                    }
                                })
                            }
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
                                        value = contactName,
                                        onValueChange = {
                                            viewModel.setContactName(it)
                                        },
                                        labelText = "Contact Name"
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    AddUpdateField(
                                        value = contactNumber,
                                        onValueChange = {
                                            viewModel.setContactNumber(it)
                                        },
                                        labelText = "Contact Number",
                                        keyBoardOption = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    AddUpdateField(
                                        value = contactAddress,
                                        onValueChange = {
                                            viewModel.setContactAddress(it)
                                        },
                                        labelText = "Contact Address",
                                        keyBoardOption = KeyboardOptions(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Done
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    AddUpdateField(
                                        value = contactEmail,
                                        onValueChange = {
                                            viewModel.setContactEmail(it)
                                        },
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
                                            val response = Permission().checkWritePermission(requireContext())
                                            if (response){
                                                when (operationType) {
                                                    ContactOperation.CONTACT_ADD.name -> {
                                                        viewModel.insertContact()
                                                        findNavController().navigate(
                                                            resId = R.id.contactFragment,
                                                            args = bundleOf(
                                                                Helper.USER_PREF to userPreferences
                                                            ),
                                                            navOptions = Helper.navOptionsWithPopStack(
                                                                destinationId = R.id.addContactFragment,
                                                                inclusive = true
                                                            )
                                                        )
                                                        Toast.makeText(
                                                            requireContext(),
                                                            "${contactName} has saved in contacts successfully :) ",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                                    ContactOperation.CONTACT_UPDATE.name -> {
                                                        viewModel.updateContact()
                                                        findNavController().navigate(
                                                            resId = R.id.contactFragment,
                                                            args = bundleOf(
                                                                Helper.USER_PREF to userPreferences
                                                            ),
                                                            navOptions = Helper.navOptionsWithPopStack(
                                                                destinationId = R.id.addContactFragment,
                                                                inclusive = true
                                                            )
                                                        )
                                                        Toast.makeText(
                                                            requireContext(),
                                                            "${contactName} has updated in contacts successfully :) ",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                                    ContactOperation.CONTACT_IMPORT_UPDATE.name -> {
                                                        viewModel.updateContactInProvider()
                                                        findNavController().navigate(
                                                            resId = R.id.contactFragment,
                                                            args = bundleOf(
                                                                Helper.USER_PREF to userPreferences
                                                            ),
                                                            navOptions = Helper.navOptionsWithPopStack(
                                                                destinationId = R.id.addContactFragment,
                                                                inclusive = true
                                                            )
                                                        )
                                                        Toast.makeText(
                                                            requireContext(),
                                                            "${contactName} has updated in contacts successfully :) ",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                                    ContactOperation.CONTACT_IMPORT_ADD.name -> {
                                                        viewModel.insertContactInProvider()
                                                        findNavController().navigate(
                                                            resId = R.id.contactFragment,
                                                            args = bundleOf(
                                                                Helper.USER_PREF to userPreferences
                                                            ),
                                                            navOptions = Helper.navOptionsWithPopStack(
                                                                destinationId = R.id.addContactFragment,
                                                                inclusive = true
                                                            )
                                                        )
                                                        Toast.makeText(
                                                            requireContext(),
                                                            "${contactName} has saved in contacts successfully :) ",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                                }
                                            }
                                        },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Blue200
                                        )
                                    ) {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = if (
                                                operationType == ContactOperation.CONTACT_UPDATE.name
                                                ||
                                                operationType == ContactOperation.CONTACT_IMPORT_UPDATE.name
                                            ) "Update" else "Save",
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
                }
            }
        }
    }
}
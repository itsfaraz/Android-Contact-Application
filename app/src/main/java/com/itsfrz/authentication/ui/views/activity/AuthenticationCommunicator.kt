package com.itsfrz.authentication.ui.views.activity

import com.itsfrz.authentication.data.indatabase.model.Contact

interface AuthenticationCommunicator {

    fun routeToLogin(username : String);
    fun routeToSignUp();
    fun routeFromLoginToLandingPage();
    fun routerFromLoginToContactPage();
    fun routerFromLoginToContactImportPage();
    fun routeFromContactToLandingPage();
    fun routerFromLandingToContactPage();
    fun routeFromContactToAddContact();
    fun routeFromContactToContactDetail(contact: Contact)
    fun routeFromContactToImportContact()

}
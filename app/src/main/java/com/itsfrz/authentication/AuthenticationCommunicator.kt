package com.itsfrz.authentication

import com.itsfrz.authentication.model.Contact

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
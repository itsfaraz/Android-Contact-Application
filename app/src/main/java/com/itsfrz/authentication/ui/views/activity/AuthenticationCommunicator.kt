package com.itsfrz.authentication.ui.views.activity

import com.itsfrz.authentication.data.entities.ContactModel

interface AuthenticationCommunicator {

    fun routeToLogin(username : String);
    fun routeToSignUp();
    fun routeFromLoginToLandingPage();
    fun routerFromLoginToContactPage();
    fun routerFromLoginToContactImportPage();
    fun routeFromContactToLandingPage();
    fun routerFromLandingToContactPage();
    fun routeFromContactToAddContact();
    fun routeFromContactToContactDetail(contact: ContactModel)
    fun routeFromContactToImportContact()
    fun routeFromContactImportToContact()

}
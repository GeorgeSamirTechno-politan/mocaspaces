package com.technopolitan.mocaspaces.data.login

import com.technopolitan.mocaspaces.utilities.Constants

class LoginMapper {

    private var userId: Int = 0
    private var userToken: String = ""
    private var firstTimeLogin: Boolean = false
    private var profileUrl: String = ""
    private var firstName: String = ""
    private var lastName: String = ""
    private var gender: String = ""
    private var brand: String = ""
    private var model: String = ""
    private var os: String = ""
    private var deviceTypes: String = ""
    private var uniquelyIdentifier: String = ""
    private var company: String = ""


    fun init(loginResponse: LoginResponse, firstTimeLogin: Boolean = false): LoginMapper {
        this.firstTimeLogin = firstTimeLogin
        loginResponse.let {
            userId = it.id!!
            userToken = it.jWToken!!
            profileUrl = it.profilePhoto!!
            firstName = it.firstName!!
            lastName = it.lastName!!
            gender = it.gender!!
            brand = it.brand!!
            model = it.model!!
            os = it.os!!
            deviceTypes = it.deviceType!!
            uniquelyIdentifier = it.uniquelyIdentifier!!
            company = it.company!!
        }
        return this
    }


    fun isSameUser(): Boolean = deviceTypes == Constants.DeviceType && brand == Constants.brand &&
            model == Constants.model && os == Constants.OS && uniquelyIdentifier == Constants.firebaseInstallationId


}

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
            it.id?.let { value -> userId = value }
            it.jWToken?.let { value -> userToken = value }
            it.profilePhoto?.let { value -> profileUrl = value }
            it.firstName?.let { value -> firstName = value }
            it.lastName?.let { value -> lastName = value }
            it.gender?.let { value -> gender = value }
            it.brand?.let { value -> brand = value }
            it.model?.let { value -> model = value }
            it.os?.let { value -> os = value }
            it.deviceType?.let { value -> deviceTypes = value }
            it.uniquelyIdentifier?.let { value -> uniquelyIdentifier = value }
            it.company?.let { value -> company = value }
        }
        return this
    }


    fun isSameUser(): Boolean = deviceTypes == Constants.DeviceType && brand == Constants.brand &&
            model == Constants.model && os == Constants.OS && uniquelyIdentifier == Constants.firebaseInstallationId


}

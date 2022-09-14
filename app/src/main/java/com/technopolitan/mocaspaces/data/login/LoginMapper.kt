package com.technopolitan.mocaspaces.data.login

import com.technopolitan.mocaspaces.network.BaseUrl

class LoginMapper {

    var userId: Int = 0
    var userToken: String = ""
    var firstTimeLogin: Boolean = false
    var profileUrl: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var gender: String = ""
    var mobile: String = ""
    var email: String = ""


    fun init(loginResponse: LoginResponse, firstTimeLogin: Boolean = false): LoginMapper {
        this.firstTimeLogin = firstTimeLogin
        loginResponse.let {
            it.id.let { value -> userId = value }
            it.jWToken.let { value -> userToken = value }
            it.profilePhoto.let { value -> profileUrl = BaseUrl.baseForImage(BaseUrl.sso) + value }
            it.firstName.let { value -> firstName = value }
            it.lastName.let { value -> lastName = value }
            it.gender.let { value -> gender = value }
            it.email.let { value -> email = value }
            it.mobileNumber.let { value -> mobile = value }
        }
        return this
    }
}

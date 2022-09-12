package com.technopolitan.mocaspaces.utilities

import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper

class Constants {
    companion object {
        const val mocaImagesPath: String = "Pictures/moca"
        val DeviceType: String = "Android"
        private const val IsAppLive: Boolean = false

        lateinit var OS: String
        lateinit var model: String
        var notificationToken: String = ""
        lateinit var applicationVersion: String
        lateinit var brand: String
        var firebaseInstallationId: String = ""
        var deviceSerial: String = ""
        const val enableLocationDirectlyRequestCode: Int = 100
        var appLanguage: String = "en"
        const val baseUrl: String = "https://testapi.techno-politan.xyz/api/"


        /// validation regex
        private const val atLeastLowerCaseRegex: String = "(?=.*[a-z])"
        private const val atLeastUpperCaseRegex: String = "(?=.*[A-Z])"
        private const val atLeastNumberCaseRegex: String = "(?=.*[0-9])"
        private const val atLeastSpecialCharacter: String = "(?=.*[\\\\W-_])"
        const val passwordRegex: String =
            "$atLeastLowerCaseRegex$atLeastUpperCaseRegex$atLeastNumberCaseRegex$atLeastSpecialCharacter{8,}"
        const val urlRegex: String =
            "^((?:.|\n)*?)((http://www.|https://www.|http://|https://)?[a-z0-9]+([-.]{1}[a-z0-9]+)([-A-Z0-9.]+)(/[-A-Z0-9+&@#/%=~_|!:,.;]*)?(?[A-Z0-9+&@#/%=~_|!:,.;]*)?)"

        /// api const value
        const val apiVersion: Int = 1

        /// permission codes
        const val cameraPermissionCode: Int = 1
        const val readExternalStoragePermissionCode: Int = 2
        const val writeExternalStoragePermissionCode: Int = 3
        const val receivingSmsPermissionCode: Int = 4
        const val networkStatusPermissionCode: Int = 5
        const val pixPermissionRequestCode: Int = 1
        var registerRequestMapper: RegisterRequestMapper = RegisterRequestMapper()


    }
}
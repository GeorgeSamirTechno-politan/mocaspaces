package com.technopolitan.mocaspaces.utilities

class Constants {
    companion object {
        val DeviceType: String = "Android"
        private const val IsAppLive: Boolean = false
        private const val baseLiveUrl: String = "https://api.copolitan.com/api/"
        private const val baseTestUrl: String = "https://testapi.techno-politan.xyz/api/"
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
        const val mobileRegex: String = "^(012|015|011|010)([0-9]{8})\$"
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
    }
}
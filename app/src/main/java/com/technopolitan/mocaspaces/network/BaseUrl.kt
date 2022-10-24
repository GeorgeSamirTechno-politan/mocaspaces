package com.technopolitan.mocaspaces.network

object BaseUrl {
    private const val appLive = false
/*    const val locationApi: String = "location"
    private const val api: String = "api"
    const val sso: String = "sso"
    const val emptyApi: String = ""
    const val setting: String = "mocasetting"*/

    const val locationApi: String = "location-management"
    private const val api: String = "api"
    const val sso: String = "sso"
    const val emptyApi: String = ""
    const val setting: String = "moca-settings"
    const val workSpaceReservation: String = "workspace-reservation"


/*    https://moca-location-api.azurewebsites.net/swagger/index.html

    fun baseUrl(serviceName: String): String{
        return if(appLive){
            "https://${serviceName}.copolitan.com/api/"
        }
        else {
            "https://${serviceName}.techno-politan.xyz/api/"
        }
    }

    fun baseForImage(serviceName: String): String{
        return if(appLive){
            "https://${serviceName}.copolitan.com/"
        }
        else {
            "https://${serviceName}.techno-politan.xyz/"
        }
    }*/
//    https://sso-api-dev.azurewebsites.net/api/v1/
//    https://moca-settings-api-dev.azurewebsites.net/api/v1/
//    https://workspace-reservation-api-dev.azurewebsites.net/api/v1/

    fun baseUrl(serviceName: String): String {
        return if (appLive) {
            "https://${serviceName}.copolitan.com/api/"
        } else {
            "https://${serviceName}-api-dev.azurewebsites.net/api/"
        }
    }

    fun baseForImage(serviceName: String): String {
        return if (appLive) {
            "https://${serviceName}.copolitan.com/"
        } else {
            "https://${serviceName}-api-dev.azurewebsites.net/"
        }
    }
}
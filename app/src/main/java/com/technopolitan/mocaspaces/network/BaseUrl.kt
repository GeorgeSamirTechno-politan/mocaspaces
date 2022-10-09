package com.technopolitan.mocaspaces.network

object BaseUrl {
    private const val appLive = false
    const val locationApi: String = "location"
    private const val api: String = "api"
    const val sso: String = "sso"
    const val emptyApi: String = ""
    const val setting: String = "mocasetting"

//    https://moca-location-api.azurewebsites.net/swagger/index.html

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
    }
}
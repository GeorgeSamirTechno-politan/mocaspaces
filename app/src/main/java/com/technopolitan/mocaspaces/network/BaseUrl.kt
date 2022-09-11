package com.technopolitan.mocaspaces.network

object BaseUrl {
    private const val appLive = false
    const val locationApi: String = "location"
    private const val testApi: String = "testapi"
    private const val api: String = "api"
    const val sso: String = "sso"
    const val emptyApi: String = ""


    fun baseUrl(serviceName: String): String{
        var serviceName = serviceName
        return if(appLive){
            if(serviceName == "")
                serviceName = api
            "https://${serviceName}.copolitan.com/api/"
        }
        else {
            if(serviceName == "")
                serviceName = testApi
            "https://${serviceName}.techno-politan.xyz/api/"
        }
    }
}
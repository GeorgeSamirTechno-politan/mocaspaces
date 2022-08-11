//package com.technopolitan.mocaspaces.modules
//
//import android.content.Context
//import android.net.ConnectivityManager
//import android.net.Network
//import android.net.NetworkCapabilities
//import android.net.NetworkRequest
//import com.technopolitan.mocaspaces.R
//import dagger.Module
//import javax.inject.Inject
//
//
//@Module
//class ConnectionStateMonitorModule @Inject constructor(
//    private var context: Context,
//    private var navigationModule: NavigationModule
//) : ConnectivityManager.NetworkCallback() {
//
//    private var networkRequest: NetworkRequest = NetworkRequest.Builder()
//        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
//        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//        .build()
//
//    fun enable() {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        connectivityManager.registerNetworkCallback(networkRequest, this)
//    }
//
//
//    // Likewise, you can have a disable method that simply calls ConnectivityManager.unregisterNetworkCallback(NetworkCallback) too.
//    override fun onAvailable(network: Network) {
//        super.onAvailable(network)
//        (navigationModule.savedStateHandler(R.id.no_internet_fragment) != null).let {
//            navigationModule.popBack()
//        }
//    }
//
//    override fun onLosing(network: Network, maxMsToLive: Int) {
//        super.onLosing(network, maxMsToLive)
//        showNoInternet()
//    }
//
//
//    override fun onLost(network: Network) {
//        super.onLost(network)
//        showNoInternet()
//    }
//
//    override fun onUnavailable() {
//        super.onUnavailable()
//        showNoInternet()
//    }
//
//    private fun showNoInternet() {
//        (navigationModule.savedStateHandler(R.id.no_internet_fragment) == null).let {
//            navigationModule.navigateTo(
//                R.id.no_internet_fragment
//            )
//        }
//    }
//}
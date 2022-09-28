package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.payment.paymentsdk.PaymentSdkActivity.Companion.startCardPayment
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.SharedPrefKey
import com.technopolitan.mocaspaces.utilities.Constants
import dagger.Module
import javax.inject.Inject

enum class CartDescription {
    Booking,
    Foodics,
    Wallet
}

@Module
class PayTabModule @Inject constructor(
    private var context: Context,
    private var sharedPrefModule: SharedPrefModule,
    private var alertModule: CustomAlertModule,
    private var activity: Activity
) :
    CallbackPaymentInterface {


    private lateinit var cartId: String
    private lateinit var cartDescription: String
    private lateinit var currency: String
    private var amount: Double = 0.0
    private lateinit var city: String
    private lateinit var district: String
    private lateinit var locationName: String

    fun init(
        cartId: String,
        cartDescription: CartDescription,
        currency: String = "EGP",
        amount: Double = 0.0,
        city: String = "cairo",
        district: String = "cairo",
        locationName: String
    ) {
        this.cartId = cartId
        this.cartDescription = getCartDescription(cartDescription)
        this.currency = currency
        this.amount = amount
        this.city = city
        this.district = district
        this.locationName = locationName
        startCardPayment((context as Activity), getPaymentSdkConfig(), this)
    }

    private fun getSdkProfileId(): String = if (Constants.IsAppLive) "81634" else "69928"
    private fun getSdkServerKey(): String =
        if (Constants.IsAppLive) "SGJNTB9ZW9-J2JTGJ2NM6-MRJNHL9TKJ" else "SMJNTB9ZRN-JBNRWRJH6G-9BZDMLZ6ZR"

    private fun getSdkClientKey(): String =
        if (Constants.IsAppLive) "CVKMKN-MR226M-67T6MQ-D2HGQP" else "C9KMKN-MRVG62-QRVR6H-6VRBPT"

    private fun getSdkScreenTitle(): String =
        if (Constants.appLanguage == "en") "Test SDK" else context.getString(R.string.app_name)

    private fun getSdkLocale(): PaymentSdkLanguageCode =
        if (Constants.appLanguage == "en") PaymentSdkLanguageCode.EN else PaymentSdkLanguageCode.AR

    private fun getSdkTokeniseType(): PaymentSdkTokenise = PaymentSdkTokenise.USER_OPTIONAL

    private fun getSdkTransType(): PaymentSdkTransactionType = PaymentSdkTransactionType.AUTH

    private fun getSdkTokenFormat(): PaymentSdkTokenFormat = PaymentSdkTokenFormat.Hex32Format()

    private fun getSdkIsoCode(): String = "EG"

    private fun getSdkBillingDetails(): PaymentSdkBillingDetails = PaymentSdkBillingDetails(
        city,
        getSdkIsoCode(),
        sharedPrefModule.getStringFromShared(SharedPrefKey.UserEmail.name),
        "${sharedPrefModule.getStringFromShared(SharedPrefKey.FirstName.name)} " +
                sharedPrefModule.getStringFromShared(SharedPrefKey.LastName.name),
        sharedPrefModule.getStringFromShared(SharedPrefKey.UserMobile.name),
        district,
        locationName,
        "4111"
    )

    private fun getPaymentSdkConfig(): PaymentSdkConfigurationDetails = PaymentSdkConfigBuilder(
        getSdkProfileId(),
        getSdkServerKey(), getSdkClientKey(), amount, currency
    )
        .setCartDescription(cartDescription)
        .setLanguageCode(getSdkLocale())
        .setMerchantIcon(AppCompatResources.getDrawable(context, R.mipmap.ic_launcher))
        .setBillingData(getSdkBillingDetails())
        .setMerchantCountryCode("EG")
        .setCartId(cartId)
        .setTransactionType(getSdkTransType())
        .showBillingInfo(false)
        .showShippingInfo(false)
        .forceShippingInfo(false)
        .setTransactionClass(PaymentSdkTransactionClass.ECOM)
        .setScreenTitle(getSdkScreenTitle())
        .hideCardScanner(false)
        .setTokenise(getSdkTokeniseType(), getSdkTokenFormat())
        .build()

    override fun onError(error: PaymentSdkError) {
        Log.d(javaClass.name, "onError: ${error.msg}")
        error.msg?.let { showAlert(it) }
    }

    override fun onPaymentCancel() {
        showAlert(context.getString(R.string.payment_cancelled))
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
        Log.d(javaClass.name, "onPaymentFinish: ${paymentSdkTransactionDetails.payResponseReturn}")
        Log.d(
            javaClass.name,
            "onPaymentFinish: ${paymentSdkTransactionDetails.paymentResult.toString()}"
        )
        Log.d(
            javaClass.name,
            "onPaymentFinish: ${paymentSdkTransactionDetails.paymentInfo.toString()}"
        )
        Log.d(javaClass.name, "onPaymentFinish: $paymentSdkTransactionDetails")
    }

    private fun showAlert(message: String) {
        alertModule.showSnakeBar(activity.window.decorView.rootView as ViewGroup, message)
    }

    /// TODO 1 for booking
    /// TODO 2 for foodic
    /// TODO 3 for wallet
    private fun getCartDescription(cartDescription: CartDescription): String =
        when (cartDescription) {
            CartDescription.Wallet -> "3"
            CartDescription.Booking -> "1"
            CartDescription.Foodics -> "2"
        }
}
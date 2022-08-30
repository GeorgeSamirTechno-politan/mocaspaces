package com.technopolitan.mocaspaces.ui.mobileOTP

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.databinding.FragmentMobileOTPBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import javax.inject.Inject

class MobileOTPFragment : Fragment() {

    private lateinit var binding: FragmentMobileOTPBinding

    @Inject
    lateinit var viewModel: MobileOTPViewModel

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var resendCodeHandler: ApiResponseModule<String>
    private lateinit var mobileNumber: String
    private lateinit var otp: String
    private lateinit var countryMapper: CountryMapper
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            viewModel.updatePermissionResult(it)
        }

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
        super.onAttach(context)
        getOtpFromArgument()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMobileOTPBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mobileNumberTextView.text = mobileNumber
        initDataModule()
    }

    private fun initDataModule(){
        binding.progressOtpMobile.spinKit.visibility = View.GONE
        viewModel.initOTPDataModule(binding.mobileNumberTextView,
            binding.changeNumberTextView,
            binding.otpIncludeLayout.otpFirstEditText,
            binding.otpIncludeLayout.otpSecondEditText,
            binding.otpIncludeLayout.otpThirdEditText,
            binding.otpIncludeLayout.otpFourthEditText,
            binding.remainingTextView,
            binding.resendTextView,
            binding.errorTextView,
            otp,
            activityResultLauncher,
            { resendCode() }
        ) { navigateToRegisterForm() }
    }

    private fun navigateToRegisterForm() {
        val bundle = Bundle()
        bundle.putString(AppKeys.MobileNumber.name, mobileNumber)
        bundle.putParcelable(AppKeys.CountryMapper.name, countryMapper)
        navigationModule.navigateTo(R.id.action_mobile_otp_personal_info, bundle = bundle)
    }

    private fun resendCode() {
        viewModel.checkMobile(mobileNumber)
        viewModel.handleCheckMobileApi().observe(
            viewLifecycleOwner,
        ) {
            resendCodeHandler.handleResponse(
                it,
                binding.progressOtpMobile.spinKit,
                binding.resendTextView
            ) {
                viewModel.updateOtp(otp)
            }
        }
    }

    private fun getOtpFromArgument() {
        arguments?.let {
            if (it.containsKey(AppKeys.MobileNumber.name))
                mobileNumber = it.getString(AppKeys.MobileNumber.name)!!
            if (it.containsKey(AppKeys.OTP.name))
                otp = it.getString(AppKeys.OTP.name)!!
            if (it.containsKey(AppKeys.CountryMapper.name))
                countryMapper = it.getParcelable(AppKeys.CountryMapper.name)!!
        }

    }

}
package com.technopolitan.mocaspaces.ui.mobileOTP

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.databinding.FragmentMobileOTPBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import javax.inject.Inject

class MobileOTPFragment : Fragment() {

    private lateinit var binding: FragmentMobileOTPBinding

    @Inject
    lateinit var viewModel: MobileOTPViewModel

    @Inject
    lateinit var resendCodeHandler: ApiResponseModule<String>
    private lateinit var mobileNumber: String
    private lateinit var otp: String

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
            { resendCode() }
        ) { navigateToRegisterForm() }
    }

    private fun navigateToRegisterForm() {
        /// TODO nav to register form
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
                viewModel.updateRemaining(otp)
            }
        }
    }

    private fun getOtpFromArgument(){
        mobileNumber = arguments?.getString(AppKeys.MobileNumber.name)!!
        otp = arguments?.getString(AppKeys.OTP.name)!!
    }

}
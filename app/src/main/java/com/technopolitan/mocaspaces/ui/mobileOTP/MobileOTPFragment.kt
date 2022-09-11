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
import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper
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

    @Inject
    lateinit var verifyMobileHandler: ApiResponseModule<String>
    private lateinit var registerRequestMapper: RegisterRequestMapper

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//            viewModel.updatePermissionResult(it)
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
        binding.mobileNumberTextView.text = registerRequestMapper.mobile
        initDataModule()
    }

    private fun initDataModule() {
        binding.progressOtpResend.spinKit.visibility = View.GONE
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
            { resendCode() }
        ) { validateOtp(it) }
    }

    private fun validateOtp(otp: String) {
        viewModel.verifyMobileOtp(registerRequestMapper.mobile, otp)
        viewModel.handleVerifyMobileOtp().observe(viewLifecycleOwner) {
            verifyMobileHandler.handleResponse(
                it,
                binding.progressOtpMobile.spinKit,
                binding.otpIncludeLayout.root
            ) {
                navigateToRegisterForm()
            }
        }
    }

    private fun navigateToRegisterForm() {
        val bundle = Bundle()
        bundle.putParcelable(AppKeys.RegisterRequestMapper.name, registerRequestMapper)
        navigationModule.navigateTo(R.id.action_mobile_otp_personal_info, bundle = bundle)
    }

    private fun resendCode() {
        viewModel.checkMobile(registerRequestMapper.mobile)
        viewModel.handleCheckMobileApi().observe(
            viewLifecycleOwner,
        ) {
            resendCodeHandler.handleResponse(
                it,
                binding.progressOtpResend.spinKit,
                binding.resendTextView
            ) {
//                viewModel.updateOtp(otp)
            }
        }
    }

    private fun getOtpFromArgument() {
        arguments?.let {
            if (it.containsKey(AppKeys.RegisterRequestMapper.name))
                registerRequestMapper = it.getParcelable(AppKeys.RegisterRequestMapper.name)!!
        }

    }

}
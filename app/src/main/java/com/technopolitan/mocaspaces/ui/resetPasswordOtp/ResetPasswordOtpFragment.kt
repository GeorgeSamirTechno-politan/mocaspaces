package com.technopolitan.mocaspaces.ui.resetPasswordOtp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.databinding.FragmentResetPasswordOtpBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import javax.inject.Inject

class ResetPasswordOtpFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordOtpBinding

    @Inject
    lateinit var viewModel: ResetPasswordOtpViewModel

    @Inject
    lateinit var navigationModule: NavigationModule


    @Inject
    lateinit var resendCodeHandler: ApiResponseModule<String>

    @Inject
    lateinit var verifyMobileHandler: ApiResponseModule<String>

    private lateinit var countryMapper: CountryMapper


    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordOtpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromArgument()
        binding.otpLayout.mobileNumberTextView.text = countryMapper.code.plus(countryMapper.mobile)
        validateOtp()
        initDataModule()
    }

    private fun getDataFromArgument(){
        arguments?.let {
            countryMapper = it.getParcelable(AppKeys.Message.name)!!

        }
    }

    private fun initDataModule() {
        binding.otpLayout.progressOtpResend.progressView.visibility = View.GONE
        binding.otpLayout.progressOtpMobile.progressView.visibility = View.GONE
        viewModel.initOTPDataModule(
            binding.otpLayout.changeNumberTextView,
            binding.otpLayout.otpIncludeLayout.otpFirstEditText,
            binding.otpLayout.otpIncludeLayout.otpSecondEditText,
            binding.otpLayout.otpIncludeLayout.otpThirdEditText,
            binding.otpLayout.otpIncludeLayout.otpFourthEditText,
            binding.otpLayout.remainingTextView,
            binding.otpLayout.resendTextView,
            binding.otpLayout.errorTextView,
            { resendCode() }
        ) {
            setOtpRequest(it)
            validateOtp()
        }
    }

    private fun setOtpRequest(it: String) {
        viewModel.verifyMobileOtp(
            countryMapper.code.plus(countryMapper.mobile),
            it
        )
    }

    private fun validateOtp() {
        viewModel.handleVerifyMobileOtp().observe(viewLifecycleOwner) {
            verifyMobileHandler.handleResponse(
                it,
                binding.otpLayout.progressOtpMobile.progressView,
                binding.otpLayout.otpIncludeLayout.root, {
                    navigateToChangePassword()
                }, {
                    viewModel.showErrorOnOtp()
                })
        }
    }

    private fun navigateToChangePassword() {
        val bundle = Bundle()
        bundle.putParcelable(AppKeys.Message.name, countryMapper)
        navigationModule.navigateTo(R.id.action_otp_forgot_password_to_reset_password, bundle = bundle)
    }

    private fun resendCode() {
        viewModel.checkMobile(countryMapper.code.plus(countryMapper.mobile))
        viewModel.handleCheckMobileApi().observe(
            viewLifecycleOwner,
        ) {
            resendCodeHandler.handleResponse(
                it,
                binding.otpLayout.progressOtpResend.progressView,
                binding.otpLayout.resendTextView
            ) {
//                viewModel.updateOtp(otp)
            }
        }
    }

}
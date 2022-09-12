package com.technopolitan.mocaspaces.ui.mobileOTP

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper
import com.technopolitan.mocaspaces.databinding.FragmentMobileOTPBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.register.RegisterViewModel
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


    @Inject
    lateinit var registerViewModel: RegisterViewModel

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
        super.onAttach(context)
//        getRegisterMapperFromArgument()
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
        registerViewModel= ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
        val mobile =
            registerViewModel.getRegisterRequestMapper().counterMapper.code + registerViewModel.getRegisterRequestMapper().mobile
        binding.mobileNumberTextView.text = mobile

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
        viewModel.verifyMobileOtp(
            registerViewModel.getRegisterRequestMapper().counterMapper.code + registerViewModel.getRegisterRequestMapper().mobile,
            otp
        )
        viewModel.handleVerifyMobileOtp().observe(viewLifecycleOwner) {
            verifyMobileHandler.handleResponse(
                it,
                binding.progressOtpMobile.spinKit,
                binding.otpIncludeLayout.root, {
                    navigateToPersonalInfo()
                }, {
                    viewModel.showErrorOnOtp()
                })
        }
    }

    private fun navigateToPersonalInfo() {
//        val bundle = Bundle()
//        bundle.putParcelable(AppKeys.RegisterRequestMapper.name, registerViewModel.getRegisterRequestMapper())
        navigationModule.navigateTo(R.id.action_mobile_otp_personal_info)
    }

    private fun resendCode() {
        viewModel.checkMobile(registerViewModel.getRegisterRequestMapper().mobile)
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

//    private fun getRegisterMapperFromArgument() {
//        arguments?.let {
//            if (it.containsKey(AppKeys.RegisterRequestMapper.name))
//                registerViewModel.getRegisterRequestMapper() = it.getParcelable(AppKeys.RegisterRequestMapper.name)!!
//        }
//
//    }

}
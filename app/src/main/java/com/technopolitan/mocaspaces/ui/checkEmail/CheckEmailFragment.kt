package com.technopolitan.mocaspaces.ui.checkEmail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentCheckEmailBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.register.RegisterViewModel
import javax.inject.Inject

class CheckEmailFragment : Fragment() {

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var registerViewModel: RegisterViewModel

    @Inject
    lateinit var viewModel: CheckEmailViewModel

    @Inject
    lateinit var sendEmailOtpApiHandler: ApiResponseModule<String>

    @Inject
    lateinit var verifyOtpApiHandler: ApiResponseModule<String>

    private lateinit var binding: FragmentCheckEmailBinding

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emailTextView.text = registerViewModel.getRegisterRequestMapper().email
        setSendEmailOtp()
        viewModel.init(binding, {
            setSendEmailOtp()
        }, {
            handleCheckEmailOtp(it)
        })
    }

    private fun setSendEmailOtp() {
        viewModel.setSendEmailOtp(registerViewModel.getRegisterRequestMapper().email)
        viewModel.handleSentOtpEmail().observe(viewLifecycleOwner) {
            sendEmailOtpApiHandler.handleResponse(
                it, binding.emailOtpLoadingLayout.spinKit,
                binding.otpEmailLayout.root
            ) {

            }
        }
    }

    private fun handleCheckEmailOtp(otp: String) {
        viewModel.verifyEmailOtp(registerViewModel.getRegisterRequestMapper().email, otp)
        viewModel.handleVerifyMobileOtp().observe(viewLifecycleOwner) {
            verifyOtpApiHandler.handleResponse(it, binding.emailOtpLoadingLayout.spinKit,
                binding.otpEmailLayout.root, {
                    navigationModule.navigateTo(R.id.action_check_email_to_password)
                }, {
                    viewModel.showErrorOnOtp()
                })
        }
    }

}
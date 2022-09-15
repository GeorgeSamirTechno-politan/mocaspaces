package com.technopolitan.mocaspaces.ui.forgetPasswordMobile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.databinding.FragmentForgetPasswordMobileBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.sharedViewModel.CountryViewModel
import javax.inject.Inject

class ForgetPasswordMobileFragment : Fragment() {

    @Inject
    lateinit var countryResponseHandler: ApiResponseModule<List<CountryMapper>>

    @Inject
    lateinit var checkMobileHandler: ApiResponseModule<String>

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var viewModel: ForgetPasswordMobileViewModel

    @Inject
    lateinit var countryViewModel: CountryViewModel

    private lateinit var binding: FragmentForgetPasswordMobileBinding


    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordMobileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.forgotPasswordAppBar.appToolBar.title = requireActivity().getString(R.string.back)
//        binding.forgotPasswordAppBar.appToolBar.isTitleCentered = false
//        binding.forgotPasswordAppBar.appToolBar.setNavigationIconTint(requireActivity().getColor(R.color.accent_color))
        binding.forgotPasswordAppBar.appToolBar.setNavigationOnClickListener{
            navigationModule.popBack()
        }
        setDataToDataModule()
        setCountryRequest()
    }

    private fun setDataToDataModule() {
        viewModel.initCheckMobileDataModule(
            binding.mobileNumberForgotPasswordLayout.mobileNumberLayout,
            binding.mobileNumberForgotPasswordLayout.countryImageView,
            binding.mobileNumberForgotPasswordLayout.countryCodeTextView,
            binding.mobileNumberForgotPasswordLayout.arrowDownCountryImageView,
            binding.mobileNumberForgotPasswordLayout.mobileNumberEditText,
            binding.forgotPasswordBtn,
        ) {
            listenForCheckMobile(it)
        }
    }

    private fun setCountryRequest() {
        countryViewModel.setCountryRequest()
        listenForCountryResponse()
    }

    private fun listenForCountryResponse() {
        countryViewModel.getCountry().observe(viewLifecycleOwner) {
            countryResponseHandler.handleResponse(
                it,
                binding.mobileNumberForgotPasswordLayout.mobileIncludeProgress.progressView,
                binding.mobileNumberForgotPasswordLayout.mobileNumberLayout
            ) { list ->
                viewModel.initCountryDropDown(list)

            }
        }
    }

    private fun listenForCheckMobile(it: CountryMapper) {
        viewModel.countryMapper = it
        viewModel.setSendMobileOtpRequest(it.code + it.mobile)
        listenForApi()
    }

    private fun listenForApi(){
        viewModel.getMobileOtp().observe(viewLifecycleOwner) {
            checkMobileHandler.handleResponse(it,
                binding.forgotPasswordBtn
            ) {
                val bundle = Bundle()
                bundle.putParcelable(AppKeys.Message.name, viewModel.countryMapper)
                navigationModule.navigateTo(R.id.action_forget_password_to_otp_forgot_password, bundle =  bundle)
            }
        }
    }
}
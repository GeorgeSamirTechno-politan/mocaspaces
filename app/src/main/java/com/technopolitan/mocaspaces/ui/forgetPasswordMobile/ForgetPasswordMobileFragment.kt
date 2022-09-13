package com.technopolitan.mocaspaces.ui.forgetPasswordMobile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.databinding.FragmentForgetPasswordMobileBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.sharedViewModel.CountryViewModel
import javax.inject.Inject

class ForgetPasswordMobileFragment : Fragment() {

    @Inject
    lateinit var countryResponseHandler: ApiResponseModule<List<CountryMapper>>

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
    ): View? {
        binding = FragmentForgetPasswordMobileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            checkMobileRequest(it.code)
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
                binding.mobileNumberForgotPasswordLayout.mobileIncludeProgress.spinKit,
                binding.mobileNumberForgotPasswordLayout.mobileNumberLayout
            ) { list ->
                viewModel.initCountryDropDown(list)

            }
        }
    }

    private fun listenForCheckMobile(it: CountryMapper) {
        TODO("Not yet implemented")
    }

    private fun checkMobileRequest(code: String) {

    }

}
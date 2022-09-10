package com.technopolitan.mocaspaces.ui.checkMobile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.country.CountryDataModule
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.databinding.FragmentCheckMobileBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.sharedViewModel.CountryViewModel
import javax.inject.Inject

class CheckMobileFragment : Fragment() {

    private lateinit var binding: FragmentCheckMobileBinding

    @Inject
    lateinit var countryDataModule: CountryDataModule

    @Inject
    lateinit var countryResponseHandler: ApiResponseModule<List<CountryMapper>>

    @Inject
    lateinit var verifyMobileHandler: ApiResponseModule<String>

    @Inject
    lateinit var checkMobileViewModel: CheckMobileViewModel

    @Inject
    lateinit var countryViewModel: CountryViewModel

    @Inject
    lateinit var navigationModule: NavigationModule


    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckMobileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataToDataModule()
        setCountryRequest()
    }

    private fun setDataToDataModule() {
        checkMobileViewModel.initCheckMobileDataModule(
            binding.mobileIncludeCheckMobile.mobileNumberLayout,
            binding.mobileIncludeCheckMobile.countryImageView,
            binding.mobileIncludeCheckMobile.countryCodeTextView,
            binding.mobileIncludeCheckMobile.arrowDownCountryImageView,
            binding.mobileIncludeCheckMobile.mobileNumberEditText,
            binding.signInTextView,
            binding.verifyButton,
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
                binding.mobileIncludeCheckMobile.mobileIncludeProgress.spinKit,
                binding.mobileIncludeCheckMobile.mobileNumberLayout
            ) { list ->
                checkMobileViewModel.initCountryDropDown(list)

            }
        }
    }

    private fun checkMobileRequest(countryCode: String) {
        checkMobileViewModel.checkMobile(
            countryCode +
                    binding.mobileIncludeCheckMobile.mobileNumberEditText.text.toString()
        )
    }

    private fun listenForCheckMobile(countryMapper: CountryMapper) {
        checkMobileViewModel.handleCheckMobileApi().observe(viewLifecycleOwner) {
            verifyMobileHandler.handleResponse(it, binding.verifyButton) { response ->
                val bundle = Bundle()
                bundle.putString(AppKeys.OTP.name, "1234")
                bundle.putParcelable(AppKeys.CountryMapper.name, countryMapper)
                bundle.putString(
                    AppKeys.MobileNumber.name,
                    checkMobileViewModel.getMobile(
                        countryMapper.code,
                        binding.mobileIncludeCheckMobile.mobileNumberEditText.text.toString()
                    )
                )
                navigationModule.navigateTo(R.id.action_verify_to_mobile_otp, bundle = bundle)
            }
        }
    }


}
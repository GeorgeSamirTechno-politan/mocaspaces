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
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.register.RegisterViewModel
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


    @Inject
    lateinit var registerViewModel: RegisterViewModel

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
        setClickOnSignIn()
    }

    private fun setClickOnSignIn() {
        binding.signInTextView.setOnClickListener {
            navigationModule.navigateTo(R.id.action_register_to_login, R.id.nav_host_fragment)
        }
    }

    private fun setDataToDataModule() {
        checkMobileViewModel.initCheckMobileDataModule(
            binding.mobileIncludeCheckMobile.mobileNumberLayout,
            binding.mobileIncludeCheckMobile.countryImageView,
            binding.mobileIncludeCheckMobile.countryCodeTextView,
            binding.mobileIncludeCheckMobile.arrowDownCountryImageView,
            binding.mobileIncludeCheckMobile.mobileNumberEditText,
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
                binding.mobileIncludeCheckMobile.mobileIncludeProgress.progressView,
                binding.mobileIncludeCheckMobile.mobileNumberLayout
            ) { list ->
                checkMobileViewModel.initCountryDropDown(list)

            }
        }
    }

    private fun checkMobileRequest(countryCode: String) {
        registerViewModel.getRegisterRequestMapper().mobile =
            binding.mobileIncludeCheckMobile.mobileNumberEditText.text.toString()
        if (countryCode == "+20") {
            if (registerViewModel.getRegisterRequestMapper().mobile.startsWith("0"))
                registerViewModel.getRegisterRequestMapper().mobile = registerViewModel.getRegisterRequestMapper().mobile.removeRange(0, 1)
        }
        checkMobileViewModel.checkMobile(
            countryCode + registerViewModel.getRegisterRequestMapper().mobile
        )
    }

    private fun listenForCheckMobile(countryMapper: CountryMapper) {
        checkMobileViewModel.handleCheckMobileApi().observe(viewLifecycleOwner) {
            verifyMobileHandler.handleResponse(it, binding.verifyButton) { response ->
                val bundle = Bundle()
                registerViewModel.getRegisterRequestMapper().counterMapper = countryMapper
//                bundle.putParcelable(AppKeys.RegisterRequestMapper.name, registerRequestMapper)
                navigationModule.navigateTo(R.id.action_verify_to_mobile_otp)
            }
        }
    }


}
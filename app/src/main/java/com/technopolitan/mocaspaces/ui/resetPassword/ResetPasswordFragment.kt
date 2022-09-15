package com.technopolitan.mocaspaces.ui.resetPassword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.databinding.FragmentResetPasswordBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.modules.UtilityModule
import javax.inject.Inject

class ResetPasswordFragment : Fragment() {


    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    private lateinit var binding: FragmentResetPasswordBinding

    @Inject
    lateinit var viewModel: ResetPasswordViewModel

    @Inject
    lateinit var handleRegisterApi: ApiResponseModule<String>

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var utilityModule: UtilityModule

    private lateinit var countryMapper: CountryMapper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromArgument()
        viewModel.initDataModule(binding) {
            viewModel.setResetPassword(
                countryMapper.mobile,
                binding.resetPasswordLayout.newPasswordEditText.text.toString()
            )
            handleRegisterApi()
        }
    }

    private fun getDataFromArgument() {
        arguments?.let {
            countryMapper = it.getParcelable(AppKeys.Message.name)!!
        }
    }

    private fun handleRegisterApi() {
        viewModel.getResetPassword().observe(viewLifecycleOwner) {
            handleRegisterApi.handleResponse(it, binding.resetPasswordBtn) {
                utilityModule.setStatusBar(R.color.accent_color)
                navigationModule.navigateTo(R.id.action_reset_password_fragment_to_password_change_successfully_fragment)
            }
        }
    }

}
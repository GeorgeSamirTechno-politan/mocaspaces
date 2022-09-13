package com.technopolitan.mocaspaces.ui.registerPassword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentRegisterPasswordBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.register.RegisterViewModel
import javax.inject.Inject

class RegisterPasswordFragment : Fragment() {

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    @Inject
    lateinit var viewModel: RegisterPasswordViewModel

    private lateinit var binding: FragmentRegisterPasswordBinding

    @Inject
    lateinit var registerViewModel: RegisterViewModel

    @Inject
    lateinit var handleRegisterApi: ApiResponseModule<String>

    @Inject
    lateinit var navigationModule: NavigationModule

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initDataModule(binding) {
            viewModel.setRegisterRequest(registerViewModel.getRegisterRequestMapper())
            handleRegisterApi()
        }
    }

    private fun handleRegisterApi() {
        viewModel.getRegisterHandler().observe(viewLifecycleOwner) {
            handleRegisterApi.handleResponse(it, binding.createAccountBtn) {
                navigationModule.navigateTo(R.id.action_fragment_password_to_success_register_dialog)
            }
        }
    }

}
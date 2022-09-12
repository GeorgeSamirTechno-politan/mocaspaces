package com.technopolitan.mocaspaces.ui.personalInfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.data.gender.GenderMapper
import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper
import com.technopolitan.mocaspaces.data.shared.MemberTypeViewModel
import com.technopolitan.mocaspaces.databinding.FragmentPersonalInfoBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.ui.register.RegisterViewModel
import javax.inject.Inject

class PersonalInfoFragment : Fragment() {

    @Inject
    lateinit var viewModel: PersonalInfoViewModel

    @Inject
    lateinit var memberTypeViewModel: MemberTypeViewModel

    @Inject
    lateinit var memberTypeResponseHandler: ApiResponseModule<List<DropDownMapper>>

    @Inject
    lateinit var genderResponseHandler: ApiResponseModule<List<GenderMapper>>

    private lateinit var binding: FragmentPersonalInfoBinding

    @Inject
    lateinit var registerViewModel: RegisterViewModel


    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
        super.onAttach(context)
//        getDataFromArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerViewModel= ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
        initDataModule()
        initMemberType()
        initGender()
    }

    private fun initMemberType() {
        memberTypeViewModel.setMemberTypeRequest()
        memberTypeViewModel.getMemberTypes().observe(viewLifecycleOwner) {
            memberTypeResponseHandler.handleResponse(
                it,
                binding.personalInfoProgress.spinKit,
                binding.memberTypeRecycler
            ) { list ->
                viewModel.setDataToAdapter(list)
            }
        }
    }

//    private fun startPixFragment(){
//        activity!.add
//    }

//    private fun getDataFromArguments() {
//        arguments?.let {
//            if (it.containsKey(AppKeys.RegisterRequestMapper.name))
//                registerRequestMapper = it.getParcelable(AppKeys.RegisterRequestMapper.name)!!
//
//        }
//
//    }

    private fun initGender() {
        viewModel.setGenderRequest()
        viewModel.getGender().observe(viewLifecycleOwner) {
            genderResponseHandler.handleResponse(
                it,
                binding.genderProgressLayout.spinKit,
                binding.genderCardLayout
            ) {
                viewModel.setGenderList(it)
            }
        }
    }

    private fun initDataModule() {
        viewModel.initDataModule(binding, registerViewModel.getRegisterRequestMapper()) {
            handleNavigation()
        }
    }

    private fun handleNavigation() {
        if (registerViewModel.getRegisterRequestMapper().memberTypeMapper.id == 1 ||
            registerViewModel.getRegisterRequestMapper().memberTypeMapper.id == 2
        ) {

        } else if (registerViewModel.getRegisterRequestMapper().memberTypeMapper.id == 3) {

        }
    }

}
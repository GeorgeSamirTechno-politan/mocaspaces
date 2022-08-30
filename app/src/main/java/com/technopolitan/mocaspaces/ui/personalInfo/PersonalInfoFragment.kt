package com.technopolitan.mocaspaces.ui.personalInfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.shared.MemberTypeViewModel
import com.technopolitan.mocaspaces.databinding.FragmentPersonalInfoBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import javax.inject.Inject

class PersonalInfoFragment : Fragment() {

    @Inject
    lateinit var viewModel: PersonalInfoViewModel

    @Inject
    lateinit var memberTypeViewModel: MemberTypeViewModel

    @Inject
    lateinit var memberTypeResponseHandler: ApiResponseModule<List<DropDownMapper>>
    private lateinit var binding: FragmentPersonalInfoBinding
    private lateinit var countryMapper: CountryMapper
    private lateinit var mobileNumber: String

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            viewModel.updatePermissionResult(it)
        }


    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
        super.onAttach(context)
        getDataFromArguments()
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
        initDataModule()
        initMemberType()
    }

    private fun initMemberType() {
        memberTypeViewModel.getMemberTypes()
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

    private fun getDataFromArguments() {
        arguments?.let {
            if (it.containsKey(AppKeys.CountryMapper.name))
                countryMapper = it.getParcelable(AppKeys.CountryMapper.name)!!
            if (it.containsKey(AppKeys.MobileNumber.name))
                mobileNumber = it.getString(AppKeys.MobileNumber.name)!!
        }

    }

    private fun initDataModule() {
        viewModel.initDataModule(binding, mobileNumber, countryMapper, activityResultLauncher) {
            /// TODO missing create account api
        }
    }

}
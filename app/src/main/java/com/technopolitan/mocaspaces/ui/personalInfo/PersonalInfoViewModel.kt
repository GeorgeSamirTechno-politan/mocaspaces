package com.technopolitan.mocaspaces.ui.personalInfo


import androidx.lifecycle.LiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.DropDownMapper

import com.technopolitan.mocaspaces.data.gender.GenderMapper
import com.technopolitan.mocaspaces.data.personalInfo.PersonalInfoDataModule
import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper
import com.technopolitan.mocaspaces.data.remote.GenderRemote
import com.technopolitan.mocaspaces.data.remote.PersonalInfoRemote
import com.technopolitan.mocaspaces.databinding.FragmentPersonalInfoBinding
import javax.inject.Inject

class PersonalInfoViewModel @Inject constructor(
    private var personalInfoRemote: PersonalInfoRemote,
    private var personalInfoDataModule: PersonalInfoDataModule,
    private var genderRemote: GenderRemote
) : BaseViewModel<List<GenderMapper>>() {


    fun initDataModule(
        binding: FragmentPersonalInfoBinding,
        registerRequestMapper: RegisterRequestMapper,
        createAccountCallBack: (entity: Boolean) -> Unit,
    ) {
        personalInfoDataModule.init(
            binding,
            registerRequestMapper,
            createAccountCallBack,
        )
    }

    fun setDataToAdapter(list: List<DropDownMapper>) {
        personalInfoDataModule.initMemberTypeAdapter(list)
    }

    fun setGenderRequest(){
        apiMediatorLiveData = genderRemote.getAllGender()
    }

    fun getGender(): LiveData<ApiStatus<List<GenderMapper>>> = apiMediatorLiveData
    fun setGenderList(it: List<GenderMapper>) {
        personalInfoDataModule.setGenderList(it)
    }

//    fun updatePermissionResult(it: Boolean) {
//        personalInfoDataModule.updatePermissionResult(it)
//    }


}
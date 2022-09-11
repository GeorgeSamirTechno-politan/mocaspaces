package com.technopolitan.mocaspaces.ui.personalInfo

import androidx.activity.result.ActivityResultLauncher
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.personalInfo.PersonalInfoDataModule
import com.technopolitan.mocaspaces.data.remote.PersonalInfoRemote
import com.technopolitan.mocaspaces.databinding.FragmentPersonalInfoBinding
import javax.inject.Inject

class PersonalInfoViewModel @Inject constructor(
    private var personalInfoRemote: PersonalInfoRemote,
    private var personalInfoDataModule: PersonalInfoDataModule,
) : BaseViewModel<List<DropDownMapper>>() {
    fun initDataModule(
        binding: FragmentPersonalInfoBinding,
        mobileNumber: String,
        countryMapper: CountryMapper,
        createAccountCallBack: (entity: Boolean) -> Unit,
    ) {
        personalInfoDataModule.init(
            binding,
            createAccountCallBack,
            mobileNumber,
            countryMapper
        )
    }

    fun setDataToAdapter(list: List<DropDownMapper>) {
        personalInfoDataModule.initMemberTypeAdapter(list)
    }

//    fun updatePermissionResult(it: Boolean) {
//        personalInfoDataModule.updatePermissionResult(it)
//    }


}
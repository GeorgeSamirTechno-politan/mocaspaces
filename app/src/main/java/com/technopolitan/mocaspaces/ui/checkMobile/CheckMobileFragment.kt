package com.technopolitan.mocaspaces.ui.checkMobile

//import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.databinding.FragmentCheckMobileBinding
import javax.inject.Inject

class CheckMobileFragment : Fragment() {


    @Inject
    lateinit var checkMobileViewModel: CheckMobileViewModel

    @Inject
    lateinit var navigationModel: CheckMobileViewModel
    private lateinit var binding: FragmentCheckMobileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckMobileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}
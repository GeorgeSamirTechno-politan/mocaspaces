package com.technopolitan.mocaspaces.ui.studentVerify

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentStudentVerifyBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent

class StudentVerifyFragment : Fragment() {

    private lateinit var binding: FragmentStudentVerifyBinding

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentVerifyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }




}
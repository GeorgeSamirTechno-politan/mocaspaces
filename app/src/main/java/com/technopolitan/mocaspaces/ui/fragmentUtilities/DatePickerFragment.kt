package com.technopolitan.mocaspaces.ui.fragmentUtilities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentDatePickerBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.NavigationModule
import sh.tyy.wheelpicker.DatePickerView
import java.util.*
import javax.inject.Inject

class DatePickerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDatePickerBinding
    private var year: Int = 1960
    private var month: Int = 1
    private var day: Int = 1

    @Inject
    lateinit var navigationModule: NavigationModule

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory()
            .buildDi(context, fragment = this, activity = requireActivity()).inject(this)
        super.onAttach(context)
        getDataFromArgument()
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDatePickerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDatePicker()
        binding.cancelTextView.setOnClickListener {
            dismiss()
        }
        binding.confirmButton.setOnClickListener {
            popBack("$year,$month,$day")
        }
    }

    private fun popBack(value: String) {
        navigationModule.savedStateHandler(R.id.date_picker_dialog)
            ?.set(AppKeys.Message.name, value)
        navigationModule.popBack()
    }

    private fun initDatePicker() {
        binding.datePickerWheelView.minDate = Date(1960, 1, 1)
        binding.datePickerWheelView.maxDate = Calendar.getInstance().time
        binding.datePickerWheelView.isCircular = true
        binding.datePickerWheelView.day = day
        binding.datePickerWheelView.month = month
        binding.datePickerWheelView.year = year

        binding.datePickerWheelView.setWheelListener(listener)
    }

    private val listener: DatePickerView.Listener = object : DatePickerView.Listener {
        override fun didSelectData(year: Int, month: Int, day: Int) {
            this@DatePickerFragment.year = year
            this@DatePickerFragment.month = month
            this@DatePickerFragment.day = day
        }
    }

    private fun getDataFromArgument() {
        arguments?.let {
            if (it.containsKey(AppKeys.Day.name))
                day = it.getInt(AppKeys.Day.name)
            if (it.containsKey(AppKeys.Year.name))
                year = it.getInt(AppKeys.Year.name)
            if (it.containsKey(AppKeys.Month.name))
                month = it.getInt(AppKeys.Month.name)
        }

    }
}
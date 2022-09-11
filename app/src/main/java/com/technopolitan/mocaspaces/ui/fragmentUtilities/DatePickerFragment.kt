package com.technopolitan.mocaspaces.ui.fragmentUtilities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.customWheelPicker.WheelDatePicker
import com.technopolitan.mocaspaces.databinding.FragmentDatePickerBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import java.util.*
import javax.inject.Inject

class DatePickerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDatePickerBinding
    private var year: Int = 1960
    private var month: Int = 1
    private var day: Int = 1
    private var maxYear: Int = 0

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var dateTimeModule: DateTimeModule

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
        binding.datePickerWheelView.setSelectedDay(day)
        binding.datePickerWheelView.setSelectedYear(year)
        binding.datePickerWheelView.setSelectedMonth(month)
        binding.datePickerWheelView.getWheelYearPicker()!!.setYearStart(1960)
        binding.datePickerWheelView.getWheelYearPicker()!!
            .setYearEnd(Calendar.getInstance().time.year - maxYear)
        binding.datePickerWheelView.setOnDateSelectedListener(listener)
    }

//    private val listener: DatePickerView.Listener = object : DatePickerView.Listener {
//        override fun didSelectData(year: Int, month: Int, day: Int) {
//            this@DatePickerFragment.year = year
//            this@DatePickerFragment.month = month
//            this@DatePickerFragment.day = day
//        }
//    }

    private val listener: WheelDatePicker.OnDateSelectedListener =
        object : WheelDatePicker.OnDateSelectedListener {
            override fun onDateSelected(picker: WheelDatePicker?, date: Date?) {
                date?.let {
                    this@DatePickerFragment.year = it.year
                    this@DatePickerFragment.month = it.month
                    this@DatePickerFragment.day = it.day
                }
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
            if (it.containsKey(AppKeys.MaxYear.name))
                maxYear = it.getInt(AppKeys.MaxYear.name)
        }

    }
}
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
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.wheelPicker.widgets.WheelDatePicker
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
    }

//    override fun onStart() {
//        super.onStart()
//        val dialog: Dialog? = dialog
//        if (dialog != null) {
//            val width = ViewGroup.LayoutParams.MATCH_PARENT
//            val height = ViewGroup.LayoutParams.WRAP_CONTENT
//            dialog.window?.setLayout(width, height)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        if (dialog != null && dialog?.window != null) {
//            dialog?.window?.setBackgroundDrawable(ColorDrawable(requireContext().getColor(android.R.color.transparent)))
//            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
//        }
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
        binding.datePickerWheelView.wheelYearPicker!!.yearStart = 1960
        val maxPikYear = dateTimeModule.getTodayDateOrTime("yyyy")!!.toInt()
        binding.datePickerWheelView.wheelYearPicker!!.yearEnd = if(maxYear > 0) maxYear else maxPikYear
        binding.datePickerWheelView.selectedDay = day
        binding.datePickerWheelView.selectedYear = year
        binding.datePickerWheelView.selectedMonth = month
        binding.datePickerWheelView.setOnDateSelectedListener(listener)
    }


    private val listener: WheelDatePicker.OnDateSelectedListener =
        WheelDatePicker.OnDateSelectedListener { _, date ->
            date?.let {
                this@DatePickerFragment.year = it.year
                this@DatePickerFragment.month = it.month
                this@DatePickerFragment.day = it.day
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
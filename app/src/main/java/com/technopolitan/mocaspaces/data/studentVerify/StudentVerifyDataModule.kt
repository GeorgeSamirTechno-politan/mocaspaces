package com.technopolitan.mocaspaces.data.studentVerify

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.card.MaterialCardView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper
import com.technopolitan.mocaspaces.databinding.FragmentStudentVerifyBinding
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.DialogModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import dagger.Module
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

@Module
class StudentVerifyDataModule @Inject constructor(
    private var context: Context,
    private var navigationModule: NavigationModule,
    private var dialogModule: DialogModule,
    private var dateTimeModule: DateTimeModule
) {

    private lateinit var binding: FragmentStudentVerifyBinding
    private lateinit var callBack: (frontCardImagePath: String, backCArdImagePath: String, expireDate: String) -> Unit
    private val frontCardPathPublisher: PublishSubject<String> = PublishSubject.create()
    private val backCardPathPublisher: PublishSubject<String> = PublishSubject.create()
    private val expireDatePublisher: PublishSubject<String> = PublishSubject.create()
    private lateinit var frontPath: String
    private lateinit var backPath: String
    private lateinit var expireDate: String
    private lateinit var viewLifecycleOwner: LifecycleOwner
    private lateinit var registerRequestMapper: RegisterRequestMapper

    fun init(
        binding: FragmentStudentVerifyBinding,
        viewLifecycleOwner: LifecycleOwner,
        registerRequestMapper: RegisterRequestMapper,
        callBack: (frontCardImagePath: String, backCArdImagePath: String, expireDate: String) -> Unit
    ) {
        this.callBack = callBack
        this.viewLifecycleOwner = viewLifecycleOwner
        this.binding = binding
        this.registerRequestMapper= registerRequestMapper
        enableButton(false)
        binding.expireDateTextInputLayout.visibility = View.GONE
        initObservables()
        setClickOnBtn()
        setClickOnFrontCard()
        setClickOnBackCard()
        setClickOnExpireDate()
    }

    private fun setClickOnExpireDate() {
        binding.expireDateEditText.setOnClickListener {
            showDatePickerDialog()
        }
        binding.expireDateTextInputLayout.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun enableButton(enable: Boolean) {
        binding.studentVerifyNextBtn.isEnabled = enable
        when (enable) {
            true -> {
                binding.studentVerifyNextBtn.alpha = 1f
            }
            else -> {
                binding.studentVerifyNextBtn.alpha = 0.5f
            }
        }

    }

    private fun initObservables(){
        initFrontCardObservable()
        initBackCardObservable()
        initFrontAndBackCardObservables()
        initExpireDateObservable()
        combineAll()
    }

    private fun initFrontCardObservable() {
        frontCardPathPublisher.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty())
                    updateCardItem(
                        binding.frontStudentCard,
                        binding.frontCardImageView,
                        binding.uploadFrontCardImageView
                    )
            }
    }

    private fun initBackCardObservable() {
        backCardPathPublisher.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty())
                    updateCardItem(
                        binding.backStudentCard,
                        binding.backCardImageView,
                        binding.uploadBackCardImageView
                    )
            }
    }

    private fun initExpireDateObservable() {
        expireDatePublisher.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun updateCardItem(
        cardItem: MaterialCardView,
        startImage: ImageView,
        endImageView: ImageView
    ) {
        cardItem.strokeWidth = 0
        cardItem.strokeColor = context.getColor(android.R.color.transparent)
        cardItem.elevation = context.resources.getDimension(com.intuit.sdp.R.dimen._5sdp)
        startImage.setColorFilter(context.getColor(R.color.accent_color))
        endImageView.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                R.drawable.ic_checkmark
            )
        )
        endImageView.setColorFilter(context.getColor(R.color.accent_color))
    }


    private fun initFrontAndBackCardObservables() {
        Observable.combineLatest(
            frontCardPathPublisher,
            backCardPathPublisher
        ) { _: String, _: String -> return@combineLatest showExpireDate() }
            .subscribe {
                if(it)
                    binding.expireDateTextInputLayout.visibility = View.VISIBLE
            }
    }

    private fun showExpireDate(): Boolean {
        return frontPath.isNotEmpty() && backPath.isNotEmpty()
    }

    private fun combineAll() {
        Observable.combineLatest(
            frontCardPathPublisher,
            backCardPathPublisher,
            expireDatePublisher
        ) { front: String, back: String, expireDate: String ->
            return@combineLatest validateAll(front, back, expireDate)
        }.subscribe {
                enableButton(it)
            }
    }

    private fun validateAll(front: String, back: String, expireDate: String): Boolean {
        return front.isNotEmpty() && back.isNotEmpty() && expireDate.isNotEmpty()
    }

    private fun setClickOnBtn(){
        binding.studentVerifyNextBtn.setOnClickListener{
            callBack(frontPath, backPath, expireDate)
        }
    }

    private fun setClickOnFrontCard(){
       binding.frontStudentCard.setOnClickListener{
           val bundle = Bundle()
           bundle.putBoolean(AppKeys.FrontCardPath.name, true)
           navigationModule.navigateTo(R.id.camera_x_fragment, bundle = bundle)
           listenForImagePathFromCustomCameraX()
       }
    }

    private fun setClickOnBackCard(){
       binding.backStudentCard.setOnClickListener{
           val bundle = Bundle()
           bundle.putBoolean(AppKeys.FrontCardPath.name, false)
           navigationModule.navigateTo(R.id.camera_x_fragment, bundle = bundle)
           listenForImagePathFromCustomCameraX()
       }
    }

    private fun listenForImagePathFromCustomCameraX(){
        navigationModule.savedStateHandler(R.id.camera_x_fragment)?.let {
            it.getLiveData(AppKeys.FrontCardPath.name, "").observe(
                viewLifecycleOwner
            ) { path->
                if(path != null && path.isNotEmpty()){
                    frontPath = path
                    frontCardPathPublisher.onNext(path)
                }
            }
            it.getLiveData(AppKeys.BackCardPath.name, "").observe(
                viewLifecycleOwner
            ) { path->
                if(path != null && path.isNotEmpty()) {
                    backPath = path
                    backCardPathPublisher.onNext(path)
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = GregorianCalendar()
        calendar.time = Calendar.getInstance().time
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        registerRequestMapper.studentCardExpiryDate?.let {
            year = it.split("-")[0].toInt()
            month = it.split("-")[1].toInt()
            day = it.split("-")[2].split("T")[0].toInt()

        }
        dialogModule.showDatePickerDialog (maxYear = calendar.get(Calendar.YEAR) + 10, useCurrentYear = true,
        year = year, month = month, day = day){ selectedYear, selectedMonth, selectedDay ->
            val dateString = "$selectedYear-$selectedMonth-${selectedDay}"
            dateTimeModule.convertDate(dateString, DateTimeConstants.yearMonthDayFormat)?.let {
                registerRequestMapper.studentCardExpiryDate = it
                expireDate = it
            }
            binding.expireDateEditText.setText("$selectedDay/$selectedMonth/$selectedYear")
            expireDatePublisher.onNext(dateString)
        }
    }


}
package com.technopolitan.mocaspaces.data.studentVerify

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.card.MaterialCardView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentStudentVerifyBinding
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.DateTimeUnits
import com.technopolitan.mocaspaces.modules.DialogModule
import com.technopolitan.mocaspaces.modules.NavigationModule
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

    fun init(
        binding: FragmentStudentVerifyBinding,
        viewLifecycleOwner: LifecycleOwner,
        callBack: (frontCardImagePath: String, backCArdImagePath: String, expireDate: String) -> Unit
    ) {
        this.callBack = callBack
        this.viewLifecycleOwner = viewLifecycleOwner
        this.binding = binding
        enableButton(false)
        binding.expireDateTextInputLayout.visibility = View.GONE
        initObservables()
        setClickOnBtn()
        setClickOnFrontCard()
        setClickOnBackCard()
        setClickOnExpireDate()
    }

    private fun setClickOnExpireDate() {
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
        ) { front: String, back: String -> return@combineLatest showExpireDate(front, back) }
            .subscribe {

            }
    }

    private fun showExpireDate(front: String, back: String): Boolean {
        if (front.isNotEmpty() && back.isNotEmpty()) {
            binding.expireDateTextInputLayout.visibility = View.VISIBLE
            return true
        }
        return false
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
        val bundle = Bundle()
        bundle.putBoolean(AppKeys.FrontCardPath.name, true)
        navigationModule.navigateTo(R.id.action_student_verification_to_camera_x, bundle = bundle)
        listenForImagePathFromCustomCameraX()
    }

    private fun setClickOnBackCard(){
        val bundle = Bundle()
        bundle.putBoolean(AppKeys.FrontCardPath.name, false)
        navigationModule.navigateTo(R.id.action_student_verification_to_camera_x, bundle = bundle)
        listenForImagePathFromCustomCameraX()
    }

    private fun listenForImagePathFromCustomCameraX(){
        navigationModule.savedStateHandler(R.id.action_student_verification_to_camera_x)?.let {
            it.getLiveData(AppKeys.FrontCardPath.name, "").observe(
                viewLifecycleOwner
            ) { path->
                frontPath = path
                frontCardPathPublisher.onNext(path)
            }
            it.getLiveData(AppKeys.BackCardPath.name, "").observe(
                viewLifecycleOwner
            ) { path->
                backPath = path
                backCardPathPublisher.onNext(path)
            }
        }
    }

    private fun showDatePickerDialog() {
        dialogModule.showDatePickerDialog (maxYear = Calendar.getInstance().time.year){ year, month, day ->
            run {
                if (dateTimeModule.diffInDates(
                        Calendar.getInstance().time,
                        Date(year, month, day),
                        DateTimeUnits.Years
                    ) >= 16
                ) {
                    val dateString = "$year/$month$day"
                    binding.expireDateEditText.setText(dateString)
                    expireDatePublisher.onNext(dateString)
                    expireDate = dateString
//                    binding.dateOfBirthTextInputLayout.error = null
                } else {
//                    binding.dateOfBirthTextInputLayout.error =
//                        context.getString(R.string.birth_date_error_message)
                }
            }
        }
    }


}
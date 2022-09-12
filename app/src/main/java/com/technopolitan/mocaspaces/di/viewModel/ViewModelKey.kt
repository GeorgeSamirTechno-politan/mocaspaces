package com.technopolitan.mocaspaces.di.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.datatransport.runtime.dagger.MapKey
import kotlin.reflect.KClass


@MustBeDocumented
@kotlin.annotation.Retention
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

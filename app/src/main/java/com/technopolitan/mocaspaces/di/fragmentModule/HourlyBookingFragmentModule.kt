package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import com.technopolitan.mocaspaces.data.bookingShared.MonthDayDataModule
import com.technopolitan.mocaspaces.data.bookingShared.SingleMultiplyDataModule
import com.technopolitan.mocaspaces.data.remote.WorkSpaceCalenderRemote
import com.technopolitan.mocaspaces.data.repo.CalenderRepo
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class HourlyBookingFragmentModule @Inject constructor(private var networkModule: NetworkModule) {

    @Singleton
    @Provides
    fun provideWorkSpaceCalenderRemote(dateTimeModule: DateTimeModule): WorkSpaceCalenderRemote =
        WorkSpaceCalenderRemote(networkModule, dateTimeModule)

    @Singleton
    @Provides
    fun provideCalenderRepo(workSpaceCalenderRemote: WorkSpaceCalenderRemote): CalenderRepo =
        CalenderRepo(workSpaceCalenderRemote)

    @Singleton
    @Provides
    fun provideMonthDayDataModule(
        context: Context,
        spannableStringModule: SpannableStringModule,
        dateTimeModule: DateTimeModule
    ): MonthDayDataModule = MonthDayDataModule(context, spannableStringModule, dateTimeModule)

    @Singleton
    @Provides
    fun provideSingleMultiplyDataModule(context: Context): SingleMultiplyDataModule =
        SingleMultiplyDataModule(context)
}
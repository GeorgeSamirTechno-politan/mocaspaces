package com.technopolitan.mocaspaces.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.ui.home.FavouriteViewModel
import com.technopolitan.mocaspaces.ui.home.HomeViewModel
import com.technopolitan.mocaspaces.ui.home.bizLounge.BizLoungeViewModel
import com.technopolitan.mocaspaces.ui.home.eventSpace.EventSpaceViewModel
import com.technopolitan.mocaspaces.ui.home.meetingSpace.MeetingRoomViewModel
import com.technopolitan.mocaspaces.ui.home.workSpace.WorkSpaceViewModel
import com.technopolitan.mocaspaces.ui.locationDetails.LocationDetailsViewModel
import com.technopolitan.mocaspaces.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorkSpaceViewModel::class)
    abstract fun bindWorkSpaceViewModel(workSpaceViewModel: WorkSpaceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MeetingRoomViewModel::class)
    abstract fun bindMeetingRoomViewModel(meetingRoomViewModel: MeetingRoomViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventSpaceViewModel::class)
    abstract fun bindEventSpaceViewModel(eventSpaceViewModel: EventSpaceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BizLoungeViewModel::class)
    abstract fun bindBizLoungeViewModel(bizLoungeViewModel: BizLoungeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    abstract fun bindLocationDetailsViewModel(locationDetailsViewModel: LocationDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteViewModel::class)
    abstract fun bindFavouriteViewModel(favouriteViewModel: FavouriteViewModel): ViewModel

}
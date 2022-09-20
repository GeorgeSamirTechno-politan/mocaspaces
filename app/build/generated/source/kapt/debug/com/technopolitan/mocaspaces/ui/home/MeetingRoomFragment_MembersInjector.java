// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.home;

import com.technopolitan.mocaspaces.data.home.MeetingRoomAdapter;
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory;
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper;
import com.technopolitan.mocaspaces.modules.ApiResponseModule;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import java.util.List;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MeetingRoomFragment_MembersInjector implements MembersInjector<MeetingRoomFragment> {
  private final Provider<MeetingRoomAdapter> meetingRoomAdapterProvider;

  private final Provider<ApiResponseModule<List<MeetingRoomMapper>>> workSpaceApiHandlerProvider;

  private final Provider<ViewModelFactory> viewModelFactoryProvider;

  public MeetingRoomFragment_MembersInjector(
      Provider<MeetingRoomAdapter> meetingRoomAdapterProvider,
      Provider<ApiResponseModule<List<MeetingRoomMapper>>> workSpaceApiHandlerProvider,
      Provider<ViewModelFactory> viewModelFactoryProvider) {
    this.meetingRoomAdapterProvider = meetingRoomAdapterProvider;
    this.workSpaceApiHandlerProvider = workSpaceApiHandlerProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<MeetingRoomFragment> create(
      Provider<MeetingRoomAdapter> meetingRoomAdapterProvider,
      Provider<ApiResponseModule<List<MeetingRoomMapper>>> workSpaceApiHandlerProvider,
      Provider<ViewModelFactory> viewModelFactoryProvider) {
    return new MeetingRoomFragment_MembersInjector(meetingRoomAdapterProvider, workSpaceApiHandlerProvider, viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(MeetingRoomFragment instance) {
    injectMeetingRoomAdapter(instance, meetingRoomAdapterProvider.get());
    injectWorkSpaceApiHandler(instance, workSpaceApiHandlerProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.MeetingRoomFragment.meetingRoomAdapter")
  public static void injectMeetingRoomAdapter(MeetingRoomFragment instance,
      MeetingRoomAdapter meetingRoomAdapter) {
    instance.meetingRoomAdapter = meetingRoomAdapter;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.MeetingRoomFragment.workSpaceApiHandler")
  public static void injectWorkSpaceApiHandler(MeetingRoomFragment instance,
      ApiResponseModule<List<MeetingRoomMapper>> workSpaceApiHandler) {
    instance.workSpaceApiHandler = workSpaceApiHandler;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.MeetingRoomFragment.viewModelFactory")
  public static void injectViewModelFactory(MeetingRoomFragment instance,
      ViewModelFactory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
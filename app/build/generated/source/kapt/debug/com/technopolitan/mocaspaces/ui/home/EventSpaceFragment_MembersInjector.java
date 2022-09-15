// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.home;

import com.technopolitan.mocaspaces.data.home.MeetingRoomAdapter;
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
public final class EventSpaceFragment_MembersInjector implements MembersInjector<EventSpaceFragment> {
  private final Provider<HomeViewModel> viewModelProvider;

  private final Provider<MeetingRoomAdapter> eventSpaceAdapterProvider;

  private final Provider<ApiResponseModule<List<MeetingRoomMapper>>> eventSpaceApiHandlerProvider;

  public EventSpaceFragment_MembersInjector(Provider<HomeViewModel> viewModelProvider,
      Provider<MeetingRoomAdapter> eventSpaceAdapterProvider,
      Provider<ApiResponseModule<List<MeetingRoomMapper>>> eventSpaceApiHandlerProvider) {
    this.viewModelProvider = viewModelProvider;
    this.eventSpaceAdapterProvider = eventSpaceAdapterProvider;
    this.eventSpaceApiHandlerProvider = eventSpaceApiHandlerProvider;
  }

  public static MembersInjector<EventSpaceFragment> create(
      Provider<HomeViewModel> viewModelProvider,
      Provider<MeetingRoomAdapter> eventSpaceAdapterProvider,
      Provider<ApiResponseModule<List<MeetingRoomMapper>>> eventSpaceApiHandlerProvider) {
    return new EventSpaceFragment_MembersInjector(viewModelProvider, eventSpaceAdapterProvider, eventSpaceApiHandlerProvider);
  }

  @Override
  public void injectMembers(EventSpaceFragment instance) {
    injectViewModel(instance, viewModelProvider.get());
    injectEventSpaceAdapter(instance, eventSpaceAdapterProvider.get());
    injectEventSpaceApiHandler(instance, eventSpaceApiHandlerProvider.get());
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.EventSpaceFragment.viewModel")
  public static void injectViewModel(EventSpaceFragment instance, HomeViewModel viewModel) {
    instance.viewModel = viewModel;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.EventSpaceFragment.eventSpaceAdapter")
  public static void injectEventSpaceAdapter(EventSpaceFragment instance,
      MeetingRoomAdapter eventSpaceAdapter) {
    instance.eventSpaceAdapter = eventSpaceAdapter;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.EventSpaceFragment.eventSpaceApiHandler")
  public static void injectEventSpaceApiHandler(EventSpaceFragment instance,
      ApiResponseModule<List<MeetingRoomMapper>> eventSpaceApiHandler) {
    instance.eventSpaceApiHandler = eventSpaceApiHandler;
  }
}
// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.home.eventSpace;

import com.technopolitan.mocaspaces.data.home.MeetingRoomAdapter;
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory;
import com.technopolitan.mocaspaces.models.location.mappers.LocationPaxMapper;
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
  private final Provider<MeetingRoomAdapter> eventSpaceAdapterProvider;

  private final Provider<ApiResponseModule<List<MeetingRoomMapper>>> eventSpaceApiHandlerProvider;

  private final Provider<ApiResponseModule<List<LocationPaxMapper>>> paxApiHandlerProvider;

  private final Provider<ViewModelFactory> viewModelFactoryProvider;

  public EventSpaceFragment_MembersInjector(Provider<MeetingRoomAdapter> eventSpaceAdapterProvider,
      Provider<ApiResponseModule<List<MeetingRoomMapper>>> eventSpaceApiHandlerProvider,
      Provider<ApiResponseModule<List<LocationPaxMapper>>> paxApiHandlerProvider,
      Provider<ViewModelFactory> viewModelFactoryProvider) {
    this.eventSpaceAdapterProvider = eventSpaceAdapterProvider;
    this.eventSpaceApiHandlerProvider = eventSpaceApiHandlerProvider;
    this.paxApiHandlerProvider = paxApiHandlerProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<EventSpaceFragment> create(
      Provider<MeetingRoomAdapter> eventSpaceAdapterProvider,
      Provider<ApiResponseModule<List<MeetingRoomMapper>>> eventSpaceApiHandlerProvider,
      Provider<ApiResponseModule<List<LocationPaxMapper>>> paxApiHandlerProvider,
      Provider<ViewModelFactory> viewModelFactoryProvider) {
    return new EventSpaceFragment_MembersInjector(eventSpaceAdapterProvider, eventSpaceApiHandlerProvider, paxApiHandlerProvider, viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(EventSpaceFragment instance) {
    injectEventSpaceAdapter(instance, eventSpaceAdapterProvider.get());
    injectEventSpaceApiHandler(instance, eventSpaceApiHandlerProvider.get());
    injectPaxApiHandler(instance, paxApiHandlerProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.eventSpace.EventSpaceFragment.eventSpaceAdapter")
  public static void injectEventSpaceAdapter(EventSpaceFragment instance,
      MeetingRoomAdapter eventSpaceAdapter) {
    instance.eventSpaceAdapter = eventSpaceAdapter;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.eventSpace.EventSpaceFragment.eventSpaceApiHandler")
  public static void injectEventSpaceApiHandler(EventSpaceFragment instance,
      ApiResponseModule<List<MeetingRoomMapper>> eventSpaceApiHandler) {
    instance.eventSpaceApiHandler = eventSpaceApiHandler;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.eventSpace.EventSpaceFragment.paxApiHandler")
  public static void injectPaxApiHandler(EventSpaceFragment instance,
      ApiResponseModule<List<LocationPaxMapper>> paxApiHandler) {
    instance.paxApiHandler = paxApiHandler;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.eventSpace.EventSpaceFragment.viewModelFactory")
  public static void injectViewModelFactory(EventSpaceFragment instance,
      ViewModelFactory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
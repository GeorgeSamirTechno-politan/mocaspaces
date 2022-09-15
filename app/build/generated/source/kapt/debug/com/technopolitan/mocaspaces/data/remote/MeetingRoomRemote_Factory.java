// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.remote;

import android.content.Context;
import com.technopolitan.mocaspaces.modules.DateTimeModule;
import com.technopolitan.mocaspaces.modules.NetworkModule;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MeetingRoomRemote_Factory implements Factory<MeetingRoomRemote> {
  private final Provider<NetworkModule> networkModuleProvider;

  private final Provider<Context> contextProvider;

  private final Provider<DateTimeModule> dateTimeModuleProvider;

  public MeetingRoomRemote_Factory(Provider<NetworkModule> networkModuleProvider,
      Provider<Context> contextProvider, Provider<DateTimeModule> dateTimeModuleProvider) {
    this.networkModuleProvider = networkModuleProvider;
    this.contextProvider = contextProvider;
    this.dateTimeModuleProvider = dateTimeModuleProvider;
  }

  @Override
  public MeetingRoomRemote get() {
    return newInstance(networkModuleProvider.get(), contextProvider.get(), dateTimeModuleProvider.get());
  }

  public static MeetingRoomRemote_Factory create(Provider<NetworkModule> networkModuleProvider,
      Provider<Context> contextProvider, Provider<DateTimeModule> dateTimeModuleProvider) {
    return new MeetingRoomRemote_Factory(networkModuleProvider, contextProvider, dateTimeModuleProvider);
  }

  public static MeetingRoomRemote newInstance(NetworkModule networkModule, Context context,
      DateTimeModule dateTimeModule) {
    return new MeetingRoomRemote(networkModule, context, dateTimeModule);
  }
}

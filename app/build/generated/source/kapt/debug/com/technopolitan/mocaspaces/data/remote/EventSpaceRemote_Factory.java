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
public final class EventSpaceRemote_Factory implements Factory<EventSpaceRemote> {
  private final Provider<NetworkModule> networkModuleProvider;

  private final Provider<Context> contextProvider;

  private final Provider<DateTimeModule> dateTimeModuleProvider;

  public EventSpaceRemote_Factory(Provider<NetworkModule> networkModuleProvider,
      Provider<Context> contextProvider, Provider<DateTimeModule> dateTimeModuleProvider) {
    this.networkModuleProvider = networkModuleProvider;
    this.contextProvider = contextProvider;
    this.dateTimeModuleProvider = dateTimeModuleProvider;
  }

  @Override
  public EventSpaceRemote get() {
    return newInstance(networkModuleProvider.get(), contextProvider.get(), dateTimeModuleProvider.get());
  }

  public static EventSpaceRemote_Factory create(Provider<NetworkModule> networkModuleProvider,
      Provider<Context> contextProvider, Provider<DateTimeModule> dateTimeModuleProvider) {
    return new EventSpaceRemote_Factory(networkModuleProvider, contextProvider, dateTimeModuleProvider);
  }

  public static EventSpaceRemote newInstance(NetworkModule networkModule, Context context,
      DateTimeModule dateTimeModule) {
    return new EventSpaceRemote(networkModule, context, dateTimeModule);
  }
}
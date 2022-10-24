// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.remote;

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
public final class WorkSpaceCalenderRemote_Factory implements Factory<WorkSpaceCalenderRemote> {
  private final Provider<NetworkModule> networkModuleProvider;

  private final Provider<DateTimeModule> dateTimeModuleProvider;

  public WorkSpaceCalenderRemote_Factory(Provider<NetworkModule> networkModuleProvider,
      Provider<DateTimeModule> dateTimeModuleProvider) {
    this.networkModuleProvider = networkModuleProvider;
    this.dateTimeModuleProvider = dateTimeModuleProvider;
  }

  @Override
  public WorkSpaceCalenderRemote get() {
    return newInstance(networkModuleProvider.get(), dateTimeModuleProvider.get());
  }

  public static WorkSpaceCalenderRemote_Factory create(
      Provider<NetworkModule> networkModuleProvider,
      Provider<DateTimeModule> dateTimeModuleProvider) {
    return new WorkSpaceCalenderRemote_Factory(networkModuleProvider, dateTimeModuleProvider);
  }

  public static WorkSpaceCalenderRemote newInstance(NetworkModule networkModule,
      DateTimeModule dateTimeModule) {
    return new WorkSpaceCalenderRemote(networkModule, dateTimeModule);
  }
}
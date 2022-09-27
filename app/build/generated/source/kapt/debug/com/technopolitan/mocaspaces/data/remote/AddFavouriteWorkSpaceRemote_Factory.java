// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.remote;

import com.technopolitan.mocaspaces.modules.NetworkModule;
import com.technopolitan.mocaspaces.modules.SharedPrefModule;
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
public final class AddFavouriteWorkSpaceRemote_Factory implements Factory<AddFavouriteWorkSpaceRemote> {
  private final Provider<NetworkModule> networkModuleProvider;

  private final Provider<SharedPrefModule> sharedPrefModuleProvider;

  public AddFavouriteWorkSpaceRemote_Factory(Provider<NetworkModule> networkModuleProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider) {
    this.networkModuleProvider = networkModuleProvider;
    this.sharedPrefModuleProvider = sharedPrefModuleProvider;
  }

  @Override
  public AddFavouriteWorkSpaceRemote get() {
    return newInstance(networkModuleProvider.get(), sharedPrefModuleProvider.get());
  }

  public static AddFavouriteWorkSpaceRemote_Factory create(
      Provider<NetworkModule> networkModuleProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider) {
    return new AddFavouriteWorkSpaceRemote_Factory(networkModuleProvider, sharedPrefModuleProvider);
  }

  public static AddFavouriteWorkSpaceRemote newInstance(NetworkModule networkModule,
      SharedPrefModule sharedPrefModule) {
    return new AddFavouriteWorkSpaceRemote(networkModule, sharedPrefModule);
  }
}
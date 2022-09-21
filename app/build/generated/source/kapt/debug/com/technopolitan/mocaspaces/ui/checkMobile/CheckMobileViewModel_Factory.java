// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.checkMobile;

import com.technopolitan.mocaspaces.data.checkMobile.CheckMobileDataModule;
import com.technopolitan.mocaspaces.data.remote.CheckMobileRemote;
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
public final class CheckMobileViewModel_Factory implements Factory<CheckMobileViewModel> {
  private final Provider<CheckMobileRemote> checkMobileRemoteProvider;

  private final Provider<CheckMobileDataModule> checkMobileDataModuleProvider;

  public CheckMobileViewModel_Factory(Provider<CheckMobileRemote> checkMobileRemoteProvider,
      Provider<CheckMobileDataModule> checkMobileDataModuleProvider) {
    this.checkMobileRemoteProvider = checkMobileRemoteProvider;
    this.checkMobileDataModuleProvider = checkMobileDataModuleProvider;
  }

  @Override
  public CheckMobileViewModel get() {
    return newInstance(checkMobileRemoteProvider.get(), checkMobileDataModuleProvider.get());
  }

  public static CheckMobileViewModel_Factory create(
      Provider<CheckMobileRemote> checkMobileRemoteProvider,
      Provider<CheckMobileDataModule> checkMobileDataModuleProvider) {
    return new CheckMobileViewModel_Factory(checkMobileRemoteProvider, checkMobileDataModuleProvider);
  }

  public static CheckMobileViewModel newInstance(CheckMobileRemote checkMobileRemote,
      CheckMobileDataModule checkMobileDataModule) {
    return new CheckMobileViewModel(checkMobileRemote, checkMobileDataModule);
  }
}

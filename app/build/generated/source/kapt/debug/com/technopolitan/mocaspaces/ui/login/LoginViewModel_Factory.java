// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.login;

import com.technopolitan.mocaspaces.data.login.LoginDataModule;
import com.technopolitan.mocaspaces.data.remote.LoginRemote;
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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<LoginRemote> loginRemoteProvider;

  private final Provider<LoginDataModule> loginDataModuleProvider;

  public LoginViewModel_Factory(Provider<LoginRemote> loginRemoteProvider,
      Provider<LoginDataModule> loginDataModuleProvider) {
    this.loginRemoteProvider = loginRemoteProvider;
    this.loginDataModuleProvider = loginDataModuleProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(loginRemoteProvider.get(), loginDataModuleProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<LoginRemote> loginRemoteProvider,
      Provider<LoginDataModule> loginDataModuleProvider) {
    return new LoginViewModel_Factory(loginRemoteProvider, loginDataModuleProvider);
  }

  public static LoginViewModel newInstance(LoginRemote loginRemote,
      LoginDataModule loginDataModule) {
    return new LoginViewModel(loginRemote, loginDataModule);
  }
}
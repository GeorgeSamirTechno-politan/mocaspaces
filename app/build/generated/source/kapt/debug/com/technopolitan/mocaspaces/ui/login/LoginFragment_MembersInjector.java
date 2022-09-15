// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.login;

import com.technopolitan.mocaspaces.data.login.LoginMapper;
import com.technopolitan.mocaspaces.modules.ApiResponseModule;
import com.technopolitan.mocaspaces.modules.NavigationModule;
import com.technopolitan.mocaspaces.modules.SharedPrefModule;
import com.technopolitan.mocaspaces.modules.UtilityModule;
import com.technopolitan.mocaspaces.modules.ValidationModule;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class LoginFragment_MembersInjector implements MembersInjector<LoginFragment> {
  private final Provider<LoginViewModel> loginViewModelProvider;

  private final Provider<ApiResponseModule<LoginMapper>> apiResponseModuleProvider;

  private final Provider<SharedPrefModule> sharedPrefModuleProvider;

  private final Provider<NavigationModule> navigationModuleProvider;

  private final Provider<ValidationModule> validationModuleProvider;

  private final Provider<UtilityModule> utilityModuleProvider;

  public LoginFragment_MembersInjector(Provider<LoginViewModel> loginViewModelProvider,
      Provider<ApiResponseModule<LoginMapper>> apiResponseModuleProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider,
      Provider<NavigationModule> navigationModuleProvider,
      Provider<ValidationModule> validationModuleProvider,
      Provider<UtilityModule> utilityModuleProvider) {
    this.loginViewModelProvider = loginViewModelProvider;
    this.apiResponseModuleProvider = apiResponseModuleProvider;
    this.sharedPrefModuleProvider = sharedPrefModuleProvider;
    this.navigationModuleProvider = navigationModuleProvider;
    this.validationModuleProvider = validationModuleProvider;
    this.utilityModuleProvider = utilityModuleProvider;
  }

  public static MembersInjector<LoginFragment> create(
      Provider<LoginViewModel> loginViewModelProvider,
      Provider<ApiResponseModule<LoginMapper>> apiResponseModuleProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider,
      Provider<NavigationModule> navigationModuleProvider,
      Provider<ValidationModule> validationModuleProvider,
      Provider<UtilityModule> utilityModuleProvider) {
    return new LoginFragment_MembersInjector(loginViewModelProvider, apiResponseModuleProvider, sharedPrefModuleProvider, navigationModuleProvider, validationModuleProvider, utilityModuleProvider);
  }

  @Override
  public void injectMembers(LoginFragment instance) {
    injectLoginViewModel(instance, loginViewModelProvider.get());
    injectApiResponseModule(instance, apiResponseModuleProvider.get());
    injectSharedPrefModule(instance, sharedPrefModuleProvider.get());
    injectNavigationModule(instance, navigationModuleProvider.get());
    injectValidationModule(instance, validationModuleProvider.get());
    injectUtilityModule(instance, utilityModuleProvider.get());
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.login.LoginFragment.loginViewModel")
  public static void injectLoginViewModel(LoginFragment instance, LoginViewModel loginViewModel) {
    instance.loginViewModel = loginViewModel;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.login.LoginFragment.apiResponseModule")
  public static void injectApiResponseModule(LoginFragment instance,
      ApiResponseModule<LoginMapper> apiResponseModule) {
    instance.apiResponseModule = apiResponseModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.login.LoginFragment.sharedPrefModule")
  public static void injectSharedPrefModule(LoginFragment instance,
      SharedPrefModule sharedPrefModule) {
    instance.sharedPrefModule = sharedPrefModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.login.LoginFragment.navigationModule")
  public static void injectNavigationModule(LoginFragment instance,
      NavigationModule navigationModule) {
    instance.navigationModule = navigationModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.login.LoginFragment.validationModule")
  public static void injectValidationModule(LoginFragment instance,
      ValidationModule validationModule) {
    instance.validationModule = validationModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.login.LoginFragment.utilityModule")
  public static void injectUtilityModule(LoginFragment instance, UtilityModule utilityModule) {
    instance.utilityModule = utilityModule;
  }
}

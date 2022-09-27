// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.register;

import com.technopolitan.mocaspaces.modules.NavigationModule;
import com.technopolitan.mocaspaces.modules.UtilityModule;
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
public final class RegisterFragment_MembersInjector implements MembersInjector<RegisterFragment> {
  private final Provider<NavigationModule> navigationModelProvider;

  private final Provider<UtilityModule> utilityModuleProvider;

  private final Provider<RegisterViewModel> registerViewModelProvider;

  public RegisterFragment_MembersInjector(Provider<NavigationModule> navigationModelProvider,
      Provider<UtilityModule> utilityModuleProvider,
      Provider<RegisterViewModel> registerViewModelProvider) {
    this.navigationModelProvider = navigationModelProvider;
    this.utilityModuleProvider = utilityModuleProvider;
    this.registerViewModelProvider = registerViewModelProvider;
  }

  public static MembersInjector<RegisterFragment> create(
      Provider<NavigationModule> navigationModelProvider,
      Provider<UtilityModule> utilityModuleProvider,
      Provider<RegisterViewModel> registerViewModelProvider) {
    return new RegisterFragment_MembersInjector(navigationModelProvider, utilityModuleProvider, registerViewModelProvider);
  }

  @Override
  public void injectMembers(RegisterFragment instance) {
    injectNavigationModel(instance, navigationModelProvider.get());
    injectUtilityModule(instance, utilityModuleProvider.get());
    injectRegisterViewModel(instance, registerViewModelProvider.get());
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.register.RegisterFragment.navigationModel")
  public static void injectNavigationModel(RegisterFragment instance,
      NavigationModule navigationModel) {
    instance.navigationModel = navigationModel;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.register.RegisterFragment.utilityModule")
  public static void injectUtilityModule(RegisterFragment instance, UtilityModule utilityModule) {
    instance.utilityModule = utilityModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.register.RegisterFragment.registerViewModel")
  public static void injectRegisterViewModel(RegisterFragment instance,
      RegisterViewModel registerViewModel) {
    instance.registerViewModel = registerViewModel;
  }
}
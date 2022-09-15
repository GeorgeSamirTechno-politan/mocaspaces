// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.splash;

import com.technopolitan.mocaspaces.modules.NavigationModule;
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
public final class SplashFragment_MembersInjector implements MembersInjector<SplashFragment> {
  private final Provider<SplashViewModel> splashViewModelProvider;

  private final Provider<NavigationModule> navigationModuleProvider;

  public SplashFragment_MembersInjector(Provider<SplashViewModel> splashViewModelProvider,
      Provider<NavigationModule> navigationModuleProvider) {
    this.splashViewModelProvider = splashViewModelProvider;
    this.navigationModuleProvider = navigationModuleProvider;
  }

  public static MembersInjector<SplashFragment> create(
      Provider<SplashViewModel> splashViewModelProvider,
      Provider<NavigationModule> navigationModuleProvider) {
    return new SplashFragment_MembersInjector(splashViewModelProvider, navigationModuleProvider);
  }

  @Override
  public void injectMembers(SplashFragment instance) {
    injectSplashViewModel(instance, splashViewModelProvider.get());
    injectNavigationModule(instance, navigationModuleProvider.get());
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.splash.SplashFragment.splashViewModel")
  public static void injectSplashViewModel(SplashFragment instance,
      SplashViewModel splashViewModel) {
    instance.splashViewModel = splashViewModel;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.splash.SplashFragment.navigationModule")
  public static void injectNavigationModule(SplashFragment instance,
      NavigationModule navigationModule) {
    instance.navigationModule = navigationModule;
  }
}

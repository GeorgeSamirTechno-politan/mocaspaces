// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.passwordSaved;

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
public final class PasswordSavedFragment_MembersInjector implements MembersInjector<PasswordSavedFragment> {
  private final Provider<NavigationModule> navigationModuleProvider;

  public PasswordSavedFragment_MembersInjector(
      Provider<NavigationModule> navigationModuleProvider) {
    this.navigationModuleProvider = navigationModuleProvider;
  }

  public static MembersInjector<PasswordSavedFragment> create(
      Provider<NavigationModule> navigationModuleProvider) {
    return new PasswordSavedFragment_MembersInjector(navigationModuleProvider);
  }

  @Override
  public void injectMembers(PasswordSavedFragment instance) {
    injectNavigationModule(instance, navigationModuleProvider.get());
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.passwordSaved.PasswordSavedFragment.navigationModule")
  public static void injectNavigationModule(PasswordSavedFragment instance,
      NavigationModule navigationModule) {
    instance.navigationModule = navigationModule;
  }
}

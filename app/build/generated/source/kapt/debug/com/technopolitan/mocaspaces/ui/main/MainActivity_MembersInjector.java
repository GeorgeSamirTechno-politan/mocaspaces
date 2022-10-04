// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.main;

import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory;
import com.technopolitan.mocaspaces.modules.AppDefaultModule;
import com.technopolitan.mocaspaces.modules.DialogModule;
import com.technopolitan.mocaspaces.modules.GlideModule;
import com.technopolitan.mocaspaces.modules.NavigationModule;
import com.technopolitan.mocaspaces.modules.PermissionModule;
import com.technopolitan.mocaspaces.modules.PixModule;
import com.technopolitan.mocaspaces.modules.SharedPrefModule;
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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<AppDefaultModule> appDefaultModelProvider;

  private final Provider<ViewModelFactory> viewModelFactoryProvider;

  private final Provider<NavigationModule> navigationModuleProvider;

  private final Provider<PixModule> pixModuleProvider;

  private final Provider<PermissionModule> permissionModuleProvider;

  private final Provider<GlideModule> glideModuleProvider;

  private final Provider<SharedPrefModule> sharedPrefModuleProvider;

  private final Provider<DialogModule> dialogModuleProvider;

  private final Provider<UtilityModule> utilityModuleProvider;

  public MainActivity_MembersInjector(Provider<AppDefaultModule> appDefaultModelProvider,
      Provider<ViewModelFactory> viewModelFactoryProvider,
      Provider<NavigationModule> navigationModuleProvider, Provider<PixModule> pixModuleProvider,
      Provider<PermissionModule> permissionModuleProvider,
      Provider<GlideModule> glideModuleProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider,
      Provider<DialogModule> dialogModuleProvider, Provider<UtilityModule> utilityModuleProvider) {
    this.appDefaultModelProvider = appDefaultModelProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
    this.navigationModuleProvider = navigationModuleProvider;
    this.pixModuleProvider = pixModuleProvider;
    this.permissionModuleProvider = permissionModuleProvider;
    this.glideModuleProvider = glideModuleProvider;
    this.sharedPrefModuleProvider = sharedPrefModuleProvider;
    this.dialogModuleProvider = dialogModuleProvider;
    this.utilityModuleProvider = utilityModuleProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<AppDefaultModule> appDefaultModelProvider,
      Provider<ViewModelFactory> viewModelFactoryProvider,
      Provider<NavigationModule> navigationModuleProvider, Provider<PixModule> pixModuleProvider,
      Provider<PermissionModule> permissionModuleProvider,
      Provider<GlideModule> glideModuleProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider,
      Provider<DialogModule> dialogModuleProvider, Provider<UtilityModule> utilityModuleProvider) {
    return new MainActivity_MembersInjector(appDefaultModelProvider, viewModelFactoryProvider, navigationModuleProvider, pixModuleProvider, permissionModuleProvider, glideModuleProvider, sharedPrefModuleProvider, dialogModuleProvider, utilityModuleProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectAppDefaultModel(instance, appDefaultModelProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
    injectNavigationModule(instance, navigationModuleProvider.get());
    injectPixModule(instance, pixModuleProvider.get());
    injectPermissionModule(instance, permissionModuleProvider.get());
    injectGlideModule(instance, glideModuleProvider.get());
    injectSharedPrefModule(instance, sharedPrefModuleProvider.get());
    injectDialogModule(instance, dialogModuleProvider.get());
    injectUtilityModule(instance, utilityModuleProvider.get());
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.main.MainActivity.appDefaultModel")
  public static void injectAppDefaultModel(MainActivity instance,
      AppDefaultModule appDefaultModel) {
    instance.appDefaultModel = appDefaultModel;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.main.MainActivity.viewModelFactory")
  public static void injectViewModelFactory(MainActivity instance,
      ViewModelFactory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.main.MainActivity.navigationModule")
  public static void injectNavigationModule(MainActivity instance,
      NavigationModule navigationModule) {
    instance.navigationModule = navigationModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.main.MainActivity.pixModule")
  public static void injectPixModule(MainActivity instance, PixModule pixModule) {
    instance.pixModule = pixModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.main.MainActivity.permissionModule")
  public static void injectPermissionModule(MainActivity instance,
      PermissionModule permissionModule) {
    instance.permissionModule = permissionModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.main.MainActivity.glideModule")
  public static void injectGlideModule(MainActivity instance, GlideModule glideModule) {
    instance.glideModule = glideModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.main.MainActivity.sharedPrefModule")
  public static void injectSharedPrefModule(MainActivity instance,
      SharedPrefModule sharedPrefModule) {
    instance.sharedPrefModule = sharedPrefModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.main.MainActivity.dialogModule")
  public static void injectDialogModule(MainActivity instance, DialogModule dialogModule) {
    instance.dialogModule = dialogModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.main.MainActivity.utilityModule")
  public static void injectUtilityModule(MainActivity instance, UtilityModule utilityModule) {
    instance.utilityModule = utilityModule;
  }
}

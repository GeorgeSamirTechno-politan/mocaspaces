// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.activityModule;

import android.app.Activity;
import android.content.Context;
import com.technopolitan.mocaspaces.data.main.CustomBottomNavigationModule;
import com.technopolitan.mocaspaces.modules.DialogModule;
import com.technopolitan.mocaspaces.modules.GlideModule;
import com.technopolitan.mocaspaces.modules.NavigationModule;
import com.technopolitan.mocaspaces.modules.SharedPrefModule;
import com.technopolitan.mocaspaces.modules.UtilityModule;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainActivityModule_ProvideCustomBottomNavigationModuleFactory implements Factory<CustomBottomNavigationModule> {
  private final MainActivityModule module;

  private final Provider<Context> contextProvider;

  private final Provider<Activity> activityProvider;

  private final Provider<SharedPrefModule> sharedPrefModuleProvider;

  private final Provider<NavigationModule> navigationModuleProvider;

  private final Provider<GlideModule> glideModuleProvider;

  private final Provider<UtilityModule> utilityModuleProvider;

  private final Provider<DialogModule> dialogModuleProvider;

  public MainActivityModule_ProvideCustomBottomNavigationModuleFactory(MainActivityModule module,
      Provider<Context> contextProvider, Provider<Activity> activityProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider,
      Provider<NavigationModule> navigationModuleProvider,
      Provider<GlideModule> glideModuleProvider, Provider<UtilityModule> utilityModuleProvider,
      Provider<DialogModule> dialogModuleProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
    this.activityProvider = activityProvider;
    this.sharedPrefModuleProvider = sharedPrefModuleProvider;
    this.navigationModuleProvider = navigationModuleProvider;
    this.glideModuleProvider = glideModuleProvider;
    this.utilityModuleProvider = utilityModuleProvider;
    this.dialogModuleProvider = dialogModuleProvider;
  }

  @Override
  public CustomBottomNavigationModule get() {
    return provideCustomBottomNavigationModule(module, contextProvider.get(), activityProvider.get(), sharedPrefModuleProvider.get(), navigationModuleProvider.get(), glideModuleProvider.get(), utilityModuleProvider.get(), dialogModuleProvider.get());
  }

  public static MainActivityModule_ProvideCustomBottomNavigationModuleFactory create(
      MainActivityModule module, Provider<Context> contextProvider,
      Provider<Activity> activityProvider, Provider<SharedPrefModule> sharedPrefModuleProvider,
      Provider<NavigationModule> navigationModuleProvider,
      Provider<GlideModule> glideModuleProvider, Provider<UtilityModule> utilityModuleProvider,
      Provider<DialogModule> dialogModuleProvider) {
    return new MainActivityModule_ProvideCustomBottomNavigationModuleFactory(module, contextProvider, activityProvider, sharedPrefModuleProvider, navigationModuleProvider, glideModuleProvider, utilityModuleProvider, dialogModuleProvider);
  }

  public static CustomBottomNavigationModule provideCustomBottomNavigationModule(
      MainActivityModule instance, Context context, Activity activity,
      SharedPrefModule sharedPrefModule, NavigationModule navigationModule, GlideModule glideModule,
      UtilityModule utilityModule, DialogModule dialogModule) {
    return Preconditions.checkNotNullFromProvides(instance.provideCustomBottomNavigationModule(context, activity, sharedPrefModule, navigationModule, glideModule, utilityModule, dialogModule));
  }
}

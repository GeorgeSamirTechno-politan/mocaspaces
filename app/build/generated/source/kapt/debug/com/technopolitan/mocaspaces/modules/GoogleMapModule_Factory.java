// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.modules;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;
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
public final class GoogleMapModule_Factory implements Factory<GoogleMapModule> {
  private final Provider<Context> contextProvider;

  private final Provider<UtilityModule> utilityModuleProvider;

  private final Provider<Fragment> fragmentProvider;

  private final Provider<PermissionModule> permissionModelProvider;

  private final Provider<Activity> activityProvider;

  public GoogleMapModule_Factory(Provider<Context> contextProvider,
      Provider<UtilityModule> utilityModuleProvider, Provider<Fragment> fragmentProvider,
      Provider<PermissionModule> permissionModelProvider, Provider<Activity> activityProvider) {
    this.contextProvider = contextProvider;
    this.utilityModuleProvider = utilityModuleProvider;
    this.fragmentProvider = fragmentProvider;
    this.permissionModelProvider = permissionModelProvider;
    this.activityProvider = activityProvider;
  }

  @Override
  public GoogleMapModule get() {
    return newInstance(contextProvider.get(), utilityModuleProvider.get(), fragmentProvider.get(), permissionModelProvider.get(), activityProvider.get());
  }

  public static GoogleMapModule_Factory create(Provider<Context> contextProvider,
      Provider<UtilityModule> utilityModuleProvider, Provider<Fragment> fragmentProvider,
      Provider<PermissionModule> permissionModelProvider, Provider<Activity> activityProvider) {
    return new GoogleMapModule_Factory(contextProvider, utilityModuleProvider, fragmentProvider, permissionModelProvider, activityProvider);
  }

  public static GoogleMapModule newInstance(Context context, UtilityModule utilityModule,
      Fragment fragment, PermissionModule permissionModel, Activity activity) {
    return new GoogleMapModule(context, utilityModule, fragment, permissionModel, activity);
  }
}

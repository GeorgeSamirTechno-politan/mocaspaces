// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.fragmentModule;

import android.content.Context;
import com.technopolitan.mocaspaces.data.customPowerMenu.CustomIconMenuAdapter;
import com.technopolitan.mocaspaces.modules.PowerMenuModule;
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
public final class CheckMobileFragmentModule_ProvidePowerMenuModuleFactory implements Factory<PowerMenuModule> {
  private final CheckMobileFragmentModule module;

  private final Provider<Context> contextProvider;

  private final Provider<CustomIconMenuAdapter> customIconMenuAdapterProvider;

  public CheckMobileFragmentModule_ProvidePowerMenuModuleFactory(CheckMobileFragmentModule module,
      Provider<Context> contextProvider,
      Provider<CustomIconMenuAdapter> customIconMenuAdapterProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
    this.customIconMenuAdapterProvider = customIconMenuAdapterProvider;
  }

  @Override
  public PowerMenuModule get() {
    return providePowerMenuModule(module, contextProvider.get(), customIconMenuAdapterProvider.get());
  }

  public static CheckMobileFragmentModule_ProvidePowerMenuModuleFactory create(
      CheckMobileFragmentModule module, Provider<Context> contextProvider,
      Provider<CustomIconMenuAdapter> customIconMenuAdapterProvider) {
    return new CheckMobileFragmentModule_ProvidePowerMenuModuleFactory(module, contextProvider, customIconMenuAdapterProvider);
  }

  public static PowerMenuModule providePowerMenuModule(CheckMobileFragmentModule instance,
      Context context, CustomIconMenuAdapter customIconMenuAdapter) {
    return Preconditions.checkNotNullFromProvides(instance.providePowerMenuModule(context, customIconMenuAdapter));
  }
}
// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.customPowerMenu;

import com.technopolitan.mocaspaces.modules.GlideModule;
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
public final class CustomIconMenuAdapter_Factory implements Factory<CustomIconMenuAdapter> {
  private final Provider<GlideModule> glideModuleProvider;

  public CustomIconMenuAdapter_Factory(Provider<GlideModule> glideModuleProvider) {
    this.glideModuleProvider = glideModuleProvider;
  }

  @Override
  public CustomIconMenuAdapter get() {
    return newInstance(glideModuleProvider.get());
  }

  public static CustomIconMenuAdapter_Factory create(Provider<GlideModule> glideModuleProvider) {
    return new CustomIconMenuAdapter_Factory(glideModuleProvider);
  }

  public static CustomIconMenuAdapter newInstance(GlideModule glideModule) {
    return new CustomIconMenuAdapter(glideModule);
  }
}
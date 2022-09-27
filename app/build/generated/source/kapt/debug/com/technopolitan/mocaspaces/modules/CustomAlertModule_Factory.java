// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.modules;

import android.content.Context;
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
public final class CustomAlertModule_Factory implements Factory<CustomAlertModule> {
  private final Provider<Context> contextProvider;

  private final Provider<DialogModule> dialogModuleProvider;

  public CustomAlertModule_Factory(Provider<Context> contextProvider,
      Provider<DialogModule> dialogModuleProvider) {
    this.contextProvider = contextProvider;
    this.dialogModuleProvider = dialogModuleProvider;
  }

  @Override
  public CustomAlertModule get() {
    return newInstance(contextProvider.get(), dialogModuleProvider.get());
  }

  public static CustomAlertModule_Factory create(Provider<Context> contextProvider,
      Provider<DialogModule> dialogModuleProvider) {
    return new CustomAlertModule_Factory(contextProvider, dialogModuleProvider);
  }

  public static CustomAlertModule newInstance(Context context, DialogModule dialogModule) {
    return new CustomAlertModule(context, dialogModule);
  }
}
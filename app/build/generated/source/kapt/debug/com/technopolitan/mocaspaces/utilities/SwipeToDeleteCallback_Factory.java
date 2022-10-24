// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.utilities;

import android.content.Context;
import com.technopolitan.mocaspaces.modules.DialogModule;
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
public final class SwipeToDeleteCallback_Factory<T> implements Factory<SwipeToDeleteCallback<T>> {
  private final Provider<Context> contextProvider;

  private final Provider<DialogModule> dialogModuleProvider;

  public SwipeToDeleteCallback_Factory(Provider<Context> contextProvider,
      Provider<DialogModule> dialogModuleProvider) {
    this.contextProvider = contextProvider;
    this.dialogModuleProvider = dialogModuleProvider;
  }

  @Override
  public SwipeToDeleteCallback<T> get() {
    return newInstance(contextProvider.get(), dialogModuleProvider.get());
  }

  public static <T> SwipeToDeleteCallback_Factory<T> create(Provider<Context> contextProvider,
      Provider<DialogModule> dialogModuleProvider) {
    return new SwipeToDeleteCallback_Factory<T>(contextProvider, dialogModuleProvider);
  }

  public static <T> SwipeToDeleteCallback<T> newInstance(Context context,
      DialogModule dialogModule) {
    return new SwipeToDeleteCallback<T>(context, dialogModule);
  }
}
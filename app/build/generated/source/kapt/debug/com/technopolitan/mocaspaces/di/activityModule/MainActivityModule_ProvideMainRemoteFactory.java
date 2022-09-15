// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.activityModule;

import com.technopolitan.mocaspaces.data.remote.MainRemote;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainActivityModule_ProvideMainRemoteFactory implements Factory<MainRemote> {
  private final MainActivityModule module;

  public MainActivityModule_ProvideMainRemoteFactory(MainActivityModule module) {
    this.module = module;
  }

  @Override
  public MainRemote get() {
    return provideMainRemote(module);
  }

  public static MainActivityModule_ProvideMainRemoteFactory create(MainActivityModule module) {
    return new MainActivityModule_ProvideMainRemoteFactory(module);
  }

  public static MainRemote provideMainRemote(MainActivityModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideMainRemote());
  }
}
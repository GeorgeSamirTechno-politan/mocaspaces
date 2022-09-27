// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.fragmentModule;

import com.technopolitan.mocaspaces.data.remote.SendOtpEmailRemote;
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
public final class CheckEmailDataModule_ProvideSendOtpEmailRemoteFactory implements Factory<SendOtpEmailRemote> {
  private final CheckEmailDataModule module;

  public CheckEmailDataModule_ProvideSendOtpEmailRemoteFactory(CheckEmailDataModule module) {
    this.module = module;
  }

  @Override
  public SendOtpEmailRemote get() {
    return provideSendOtpEmailRemote(module);
  }

  public static CheckEmailDataModule_ProvideSendOtpEmailRemoteFactory create(
      CheckEmailDataModule module) {
    return new CheckEmailDataModule_ProvideSendOtpEmailRemoteFactory(module);
  }

  public static SendOtpEmailRemote provideSendOtpEmailRemote(CheckEmailDataModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideSendOtpEmailRemote());
  }
}
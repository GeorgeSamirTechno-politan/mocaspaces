// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.remote;

import com.technopolitan.mocaspaces.modules.NetworkModule;
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
public final class MemberTypeRemote_Factory implements Factory<MemberTypeRemote> {
  private final Provider<NetworkModule> networkModuleProvider;

  public MemberTypeRemote_Factory(Provider<NetworkModule> networkModuleProvider) {
    this.networkModuleProvider = networkModuleProvider;
  }

  @Override
  public MemberTypeRemote get() {
    return newInstance(networkModuleProvider.get());
  }

  public static MemberTypeRemote_Factory create(Provider<NetworkModule> networkModuleProvider) {
    return new MemberTypeRemote_Factory(networkModuleProvider);
  }

  public static MemberTypeRemote newInstance(NetworkModule networkModule) {
    return new MemberTypeRemote(networkModule);
  }
}
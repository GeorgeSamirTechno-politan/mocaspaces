// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.home;

import com.technopolitan.mocaspaces.data.shared.CountDownModule;
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
public final class PriceAdapter_Factory implements Factory<PriceAdapter> {
  private final Provider<CountDownModule> countDownModuleProvider;

  public PriceAdapter_Factory(Provider<CountDownModule> countDownModuleProvider) {
    this.countDownModuleProvider = countDownModuleProvider;
  }

  @Override
  public PriceAdapter get() {
    return newInstance(countDownModuleProvider.get());
  }

  public static PriceAdapter_Factory create(Provider<CountDownModule> countDownModuleProvider) {
    return new PriceAdapter_Factory(countDownModuleProvider);
  }

  public static PriceAdapter newInstance(CountDownModule countDownModule) {
    return new PriceAdapter(countDownModule);
  }
}
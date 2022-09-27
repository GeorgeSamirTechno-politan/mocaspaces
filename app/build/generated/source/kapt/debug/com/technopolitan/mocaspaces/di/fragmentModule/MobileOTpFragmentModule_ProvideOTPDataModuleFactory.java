// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.fragmentModule;

import android.content.Context;
import com.technopolitan.mocaspaces.data.shared.OTPDataModule;
import com.technopolitan.mocaspaces.data.shared.OtpBlockUserModule;
import com.technopolitan.mocaspaces.modules.NavigationModule;
import com.technopolitan.mocaspaces.modules.RXModule;
import com.technopolitan.mocaspaces.modules.SmsIdentifierModule;
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
public final class MobileOTpFragmentModule_ProvideOTPDataModuleFactory implements Factory<OTPDataModule> {
  private final MobileOTpFragmentModule module;

  private final Provider<Context> contextProvider;

  private final Provider<RXModule> rxModuleProvider;

  private final Provider<OtpBlockUserModule> otpBlockUserModuleProvider;

  private final Provider<NavigationModule> navigationModuleProvider;

  private final Provider<SmsIdentifierModule> smsIdentifierModuleProvider;

  private final Provider<UtilityModule> utilityModuleProvider;

  public MobileOTpFragmentModule_ProvideOTPDataModuleFactory(MobileOTpFragmentModule module,
      Provider<Context> contextProvider, Provider<RXModule> rxModuleProvider,
      Provider<OtpBlockUserModule> otpBlockUserModuleProvider,
      Provider<NavigationModule> navigationModuleProvider,
      Provider<SmsIdentifierModule> smsIdentifierModuleProvider,
      Provider<UtilityModule> utilityModuleProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
    this.rxModuleProvider = rxModuleProvider;
    this.otpBlockUserModuleProvider = otpBlockUserModuleProvider;
    this.navigationModuleProvider = navigationModuleProvider;
    this.smsIdentifierModuleProvider = smsIdentifierModuleProvider;
    this.utilityModuleProvider = utilityModuleProvider;
  }

  @Override
  public OTPDataModule get() {
    return provideOTPDataModule(module, contextProvider.get(), rxModuleProvider.get(), otpBlockUserModuleProvider.get(), navigationModuleProvider.get(), smsIdentifierModuleProvider.get(), utilityModuleProvider.get());
  }

  public static MobileOTpFragmentModule_ProvideOTPDataModuleFactory create(
      MobileOTpFragmentModule module, Provider<Context> contextProvider,
      Provider<RXModule> rxModuleProvider, Provider<OtpBlockUserModule> otpBlockUserModuleProvider,
      Provider<NavigationModule> navigationModuleProvider,
      Provider<SmsIdentifierModule> smsIdentifierModuleProvider,
      Provider<UtilityModule> utilityModuleProvider) {
    return new MobileOTpFragmentModule_ProvideOTPDataModuleFactory(module, contextProvider, rxModuleProvider, otpBlockUserModuleProvider, navigationModuleProvider, smsIdentifierModuleProvider, utilityModuleProvider);
  }

  public static OTPDataModule provideOTPDataModule(MobileOTpFragmentModule instance,
      Context context, RXModule rxModule, OtpBlockUserModule otpBlockUserModule,
      NavigationModule navigationModule, SmsIdentifierModule smsIdentifierModule,
      UtilityModule utilityModule) {
    return Preconditions.checkNotNullFromProvides(instance.provideOTPDataModule(context, rxModule, otpBlockUserModule, navigationModule, smsIdentifierModule, utilityModule));
  }
}

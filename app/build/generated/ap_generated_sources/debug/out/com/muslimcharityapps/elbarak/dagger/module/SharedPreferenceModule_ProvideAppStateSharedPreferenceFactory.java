package com.muslimcharityapps.elbarak.dagger.module;

import android.content.SharedPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SharedPreferenceModule_ProvideAppStateSharedPreferenceFactory
    implements Factory<SharedPreferences> {
  private final SharedPreferenceModule module;

  public SharedPreferenceModule_ProvideAppStateSharedPreferenceFactory(
      SharedPreferenceModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public SharedPreferences get() {
    return Preconditions.checkNotNull(
        module.provideAppStateSharedPreference(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<SharedPreferences> create(SharedPreferenceModule module) {
    return new SharedPreferenceModule_ProvideAppStateSharedPreferenceFactory(module);
  }

  /** Proxies {@link SharedPreferenceModule#provideAppStateSharedPreference()}. */
  public static SharedPreferences proxyProvideAppStateSharedPreference(
      SharedPreferenceModule instance) {
    return instance.provideAppStateSharedPreference();
  }
}

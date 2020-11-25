package com.muslimcharityapps.elbarak.dagger.module;

import android.content.SharedPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SharedPreferenceModule_ProvideAppStateEditorFactory
    implements Factory<SharedPreferences.Editor> {
  private final SharedPreferenceModule module;

  private final Provider<SharedPreferences> songStateSharedPreferenceProvider;

  public SharedPreferenceModule_ProvideAppStateEditorFactory(
      SharedPreferenceModule module,
      Provider<SharedPreferences> songStateSharedPreferenceProvider) {
    assert module != null;
    this.module = module;
    assert songStateSharedPreferenceProvider != null;
    this.songStateSharedPreferenceProvider = songStateSharedPreferenceProvider;
  }

  @Override
  public SharedPreferences.Editor get() {
    return Preconditions.checkNotNull(
        module.provideAppStateEditor(songStateSharedPreferenceProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<SharedPreferences.Editor> create(
      SharedPreferenceModule module,
      Provider<SharedPreferences> songStateSharedPreferenceProvider) {
    return new SharedPreferenceModule_ProvideAppStateEditorFactory(
        module, songStateSharedPreferenceProvider);
  }

  /** Proxies {@link SharedPreferenceModule#provideAppStateEditor(SharedPreferences)}. */
  public static SharedPreferences.Editor proxyProvideAppStateEditor(
      SharedPreferenceModule instance, SharedPreferences songStateSharedPreference) {
    return instance.provideAppStateEditor(songStateSharedPreference);
  }
}

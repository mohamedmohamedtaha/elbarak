package com.muslimcharityapps.elbarak.dagger.module;

import android.media.AudioManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UtilityModule_ProvideAudioManagerFactory implements Factory<AudioManager> {
  private final UtilityModule module;

  public UtilityModule_ProvideAudioManagerFactory(UtilityModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public AudioManager get() {
    return Preconditions.checkNotNull(
        module.provideAudioManager(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<AudioManager> create(UtilityModule module) {
    return new UtilityModule_ProvideAudioManagerFactory(module);
  }

  /** Proxies {@link UtilityModule#provideAudioManager()}. */
  public static AudioManager proxyProvideAudioManager(UtilityModule instance) {
    return instance.provideAudioManager();
  }
}

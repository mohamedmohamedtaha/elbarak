package com.muslimcharityapps.elbarak.dagger.module;

import android.media.MediaPlayer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UtilityModule_ProvideMediaPlayerFactory implements Factory<MediaPlayer> {
  private final UtilityModule module;

  public UtilityModule_ProvideMediaPlayerFactory(UtilityModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public MediaPlayer get() {
    return Preconditions.checkNotNull(
        module.provideMediaPlayer(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<MediaPlayer> create(UtilityModule module) {
    return new UtilityModule_ProvideMediaPlayerFactory(module);
  }

  /** Proxies {@link UtilityModule#provideMediaPlayer()}. */
  public static MediaPlayer proxyProvideMediaPlayer(UtilityModule instance) {
    return instance.provideMediaPlayer();
  }
}

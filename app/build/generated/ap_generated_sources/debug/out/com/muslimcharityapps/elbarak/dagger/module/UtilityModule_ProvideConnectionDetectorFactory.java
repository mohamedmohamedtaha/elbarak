package com.muslimcharityapps.elbarak.dagger.module;

import com.muslimcharityapps.elbarak.utils.ConnectionDetector;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UtilityModule_ProvideConnectionDetectorFactory
    implements Factory<ConnectionDetector> {
  private final UtilityModule module;

  public UtilityModule_ProvideConnectionDetectorFactory(UtilityModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public ConnectionDetector get() {
    return Preconditions.checkNotNull(
        module.provideConnectionDetector(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<ConnectionDetector> create(UtilityModule module) {
    return new UtilityModule_ProvideConnectionDetectorFactory(module);
  }

  /** Proxies {@link UtilityModule#provideConnectionDetector()}. */
  public static ConnectionDetector proxyProvideConnectionDetector(UtilityModule instance) {
    return instance.provideConnectionDetector();
  }
}

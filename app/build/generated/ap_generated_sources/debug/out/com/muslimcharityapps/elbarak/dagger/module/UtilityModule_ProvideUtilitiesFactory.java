package com.muslimcharityapps.elbarak.dagger.module;

import com.muslimcharityapps.elbarak.utils.Utilities;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UtilityModule_ProvideUtilitiesFactory implements Factory<Utilities> {
  private final UtilityModule module;

  public UtilityModule_ProvideUtilitiesFactory(UtilityModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Utilities get() {
    return Preconditions.checkNotNull(
        module.provideUtilities(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Utilities> create(UtilityModule module) {
    return new UtilityModule_ProvideUtilitiesFactory(module);
  }

  /** Proxies {@link UtilityModule#provideUtilities()}. */
  public static Utilities proxyProvideUtilities(UtilityModule instance) {
    return instance.provideUtilities();
  }
}

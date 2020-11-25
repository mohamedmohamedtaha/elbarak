package com.muslimcharityapps.elbarak.dagger.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RetroModule_ProvideRetroFactory implements Factory<Retrofit> {
  private final RetroModule module;

  public RetroModule_ProvideRetroFactory(RetroModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Retrofit get() {
    return Preconditions.checkNotNull(
        module.provideRetro(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Retrofit> create(RetroModule module) {
    return new RetroModule_ProvideRetroFactory(module);
  }

  /** Proxies {@link RetroModule#provideRetro()}. */
  public static Retrofit proxyProvideRetro(RetroModule instance) {
    return instance.provideRetro();
  }
}

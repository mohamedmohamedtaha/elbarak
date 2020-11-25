package com.muslimcharityapps.elbarak.dagger.module;

import com.muslimcharityapps.elbarak.retro.RetroWS;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RetroModule_ProvideRetroWSFactory implements Factory<RetroWS> {
  private final RetroModule module;

  private final Provider<Retrofit> retrofitProvider;

  public RetroModule_ProvideRetroWSFactory(
      RetroModule module, Provider<Retrofit> retrofitProvider) {
    assert module != null;
    this.module = module;
    assert retrofitProvider != null;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public RetroWS get() {
    return Preconditions.checkNotNull(
        module.provideRetroWS(retrofitProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<RetroWS> create(RetroModule module, Provider<Retrofit> retrofitProvider) {
    return new RetroModule_ProvideRetroWSFactory(module, retrofitProvider);
  }

  /** Proxies {@link RetroModule#provideRetroWS(Retrofit)}. */
  public static RetroWS proxyProvideRetroWS(RetroModule instance, Retrofit retrofit) {
    return instance.provideRetroWS(retrofit);
  }
}

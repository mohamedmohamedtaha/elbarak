package com.muslimcharityapps.elbarak.dagger.component;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import com.muslimcharityapps.elbarak.activity.Dashboard;
import com.muslimcharityapps.elbarak.activity.Dashboard_MembersInjector;
import com.muslimcharityapps.elbarak.activity.PlayListActivity;
import com.muslimcharityapps.elbarak.activity.PlayListActivity_MembersInjector;
import com.muslimcharityapps.elbarak.activity.PlayListFavorite;
import com.muslimcharityapps.elbarak.activity.PlayListFavorite_MembersInjector;
import com.muslimcharityapps.elbarak.dagger.module.RetroModule;
import com.muslimcharityapps.elbarak.dagger.module.RetroModule_ProvideRetroFactory;
import com.muslimcharityapps.elbarak.dagger.module.RetroModule_ProvideRetroWSFactory;
import com.muslimcharityapps.elbarak.dagger.module.SharedPreferenceModule;
import com.muslimcharityapps.elbarak.dagger.module.SharedPreferenceModule_ProvideAppStateEditorFactory;
import com.muslimcharityapps.elbarak.dagger.module.SharedPreferenceModule_ProvideAppStateSharedPreferenceFactory;
import com.muslimcharityapps.elbarak.dagger.module.UtilityModule;
import com.muslimcharityapps.elbarak.dagger.module.UtilityModule_ProvideAudioManagerFactory;
import com.muslimcharityapps.elbarak.dagger.module.UtilityModule_ProvideConnectionDetectorFactory;
import com.muslimcharityapps.elbarak.dagger.module.UtilityModule_ProvideMediaPlayerFactory;
import com.muslimcharityapps.elbarak.dagger.module.UtilityModule_ProvideUtilitiesFactory;
import com.muslimcharityapps.elbarak.retro.RetroWS;
import com.muslimcharityapps.elbarak.utils.ConnectionDetector;
import com.muslimcharityapps.elbarak.utils.Utilities;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerRetroComponent implements RetroComponent {
  private Provider<Retrofit> provideRetroProvider;

  private Provider<RetroWS> provideRetroWSProvider;

  private Provider<SharedPreferences> provideAppStateSharedPreferenceProvider;

  private Provider<SharedPreferences.Editor> provideAppStateEditorProvider;

  private Provider<ConnectionDetector> provideConnectionDetectorProvider;

  private Provider<MediaPlayer> provideMediaPlayerProvider;

  private Provider<AudioManager> provideAudioManagerProvider;

  private Provider<Utilities> provideUtilitiesProvider;

  private MembersInjector<Dashboard> dashboardMembersInjector;

  private MembersInjector<PlayListActivity> playListActivityMembersInjector;

  private MembersInjector<PlayListFavorite> playListFavoriteMembersInjector;

  private DaggerRetroComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.provideRetroProvider =
        DoubleCheck.provider(RetroModule_ProvideRetroFactory.create(builder.retroModule));

    this.provideRetroWSProvider =
        DoubleCheck.provider(
            RetroModule_ProvideRetroWSFactory.create(builder.retroModule, provideRetroProvider));

    this.provideAppStateSharedPreferenceProvider =
        DoubleCheck.provider(
            SharedPreferenceModule_ProvideAppStateSharedPreferenceFactory.create(
                builder.sharedPreferenceModule));

    this.provideAppStateEditorProvider =
        DoubleCheck.provider(
            SharedPreferenceModule_ProvideAppStateEditorFactory.create(
                builder.sharedPreferenceModule, provideAppStateSharedPreferenceProvider));

    this.provideConnectionDetectorProvider =
        DoubleCheck.provider(
            UtilityModule_ProvideConnectionDetectorFactory.create(builder.utilityModule));

    this.provideMediaPlayerProvider =
        DoubleCheck.provider(UtilityModule_ProvideMediaPlayerFactory.create(builder.utilityModule));

    this.provideAudioManagerProvider =
        DoubleCheck.provider(
            UtilityModule_ProvideAudioManagerFactory.create(builder.utilityModule));

    this.provideUtilitiesProvider =
        DoubleCheck.provider(UtilityModule_ProvideUtilitiesFactory.create(builder.utilityModule));

    this.dashboardMembersInjector =
        Dashboard_MembersInjector.create(
            provideRetroWSProvider,
            provideAppStateSharedPreferenceProvider,
            provideAppStateEditorProvider,
            provideConnectionDetectorProvider,
            provideMediaPlayerProvider,
            provideAudioManagerProvider,
            provideUtilitiesProvider);

    this.playListActivityMembersInjector =
        PlayListActivity_MembersInjector.create(
            provideUtilitiesProvider,
            provideAppStateSharedPreferenceProvider,
            provideAppStateEditorProvider);

    this.playListFavoriteMembersInjector =
        PlayListFavorite_MembersInjector.create(
            provideUtilitiesProvider,
            provideAppStateSharedPreferenceProvider,
            provideAppStateEditorProvider);
  }

  @Override
  public void inject(Dashboard dashboard) {
    dashboardMembersInjector.injectMembers(dashboard);
  }

  @Override
  public void inject(PlayListActivity playListActivity) {
    playListActivityMembersInjector.injectMembers(playListActivity);
  }

  @Override
  public void inject(PlayListFavorite playListFavorite) {
    playListFavoriteMembersInjector.injectMembers(playListFavorite);
  }

  public static final class Builder {
    private RetroModule retroModule;

    private SharedPreferenceModule sharedPreferenceModule;

    private UtilityModule utilityModule;

    private Builder() {}

    public RetroComponent build() {
      if (retroModule == null) {
        throw new IllegalStateException(RetroModule.class.getCanonicalName() + " must be set");
      }
      if (sharedPreferenceModule == null) {
        throw new IllegalStateException(
            SharedPreferenceModule.class.getCanonicalName() + " must be set");
      }
      if (utilityModule == null) {
        throw new IllegalStateException(UtilityModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerRetroComponent(this);
    }

    public Builder retroModule(RetroModule retroModule) {
      this.retroModule = Preconditions.checkNotNull(retroModule);
      return this;
    }

    public Builder sharedPreferenceModule(SharedPreferenceModule sharedPreferenceModule) {
      this.sharedPreferenceModule = Preconditions.checkNotNull(sharedPreferenceModule);
      return this;
    }

    public Builder utilityModule(UtilityModule utilityModule) {
      this.utilityModule = Preconditions.checkNotNull(utilityModule);
      return this;
    }
  }
}

package com.muslimcharityapps.elbarak.activity;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import com.muslimcharityapps.elbarak.retro.RetroWS;
import com.muslimcharityapps.elbarak.utils.ConnectionDetector;
import com.muslimcharityapps.elbarak.utils.Utilities;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Dashboard_MembersInjector implements MembersInjector<Dashboard> {
  private final Provider<RetroWS> retroWSProvider;

  private final Provider<SharedPreferences> appStateSharedPreferenceProvider;

  private final Provider<SharedPreferences.Editor> appStateEditorProvider;

  private final Provider<ConnectionDetector> connectionDetectorProvider;

  private final Provider<MediaPlayer> mediaPlayerProvider;

  private final Provider<AudioManager> audioManagerProvider;

  private final Provider<Utilities> utilsProvider;

  public Dashboard_MembersInjector(
      Provider<RetroWS> retroWSProvider,
      Provider<SharedPreferences> appStateSharedPreferenceProvider,
      Provider<SharedPreferences.Editor> appStateEditorProvider,
      Provider<ConnectionDetector> connectionDetectorProvider,
      Provider<MediaPlayer> mediaPlayerProvider,
      Provider<AudioManager> audioManagerProvider,
      Provider<Utilities> utilsProvider) {
    assert retroWSProvider != null;
    this.retroWSProvider = retroWSProvider;
    assert appStateSharedPreferenceProvider != null;
    this.appStateSharedPreferenceProvider = appStateSharedPreferenceProvider;
    assert appStateEditorProvider != null;
    this.appStateEditorProvider = appStateEditorProvider;
    assert connectionDetectorProvider != null;
    this.connectionDetectorProvider = connectionDetectorProvider;
    assert mediaPlayerProvider != null;
    this.mediaPlayerProvider = mediaPlayerProvider;
    assert audioManagerProvider != null;
    this.audioManagerProvider = audioManagerProvider;
    assert utilsProvider != null;
    this.utilsProvider = utilsProvider;
  }

  public static MembersInjector<Dashboard> create(
      Provider<RetroWS> retroWSProvider,
      Provider<SharedPreferences> appStateSharedPreferenceProvider,
      Provider<SharedPreferences.Editor> appStateEditorProvider,
      Provider<ConnectionDetector> connectionDetectorProvider,
      Provider<MediaPlayer> mediaPlayerProvider,
      Provider<AudioManager> audioManagerProvider,
      Provider<Utilities> utilsProvider) {
    return new Dashboard_MembersInjector(
        retroWSProvider,
        appStateSharedPreferenceProvider,
        appStateEditorProvider,
        connectionDetectorProvider,
        mediaPlayerProvider,
        audioManagerProvider,
        utilsProvider);
  }

  @Override
  public void injectMembers(Dashboard instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.retroWS = retroWSProvider.get();
    instance.appStateSharedPreference = appStateSharedPreferenceProvider.get();
    instance.appStateEditor = appStateEditorProvider.get();
    instance.connectionDetector = connectionDetectorProvider.get();
    instance.mediaPlayer = mediaPlayerProvider.get();
    instance.audioManager = audioManagerProvider.get();
    instance.utils = utilsProvider.get();
  }

  public static void injectRetroWS(Dashboard instance, Provider<RetroWS> retroWSProvider) {
    instance.retroWS = retroWSProvider.get();
  }

  public static void injectAppStateSharedPreference(
      Dashboard instance, Provider<SharedPreferences> appStateSharedPreferenceProvider) {
    instance.appStateSharedPreference = appStateSharedPreferenceProvider.get();
  }

  public static void injectAppStateEditor(
      Dashboard instance, Provider<SharedPreferences.Editor> appStateEditorProvider) {
    instance.appStateEditor = appStateEditorProvider.get();
  }

  public static void injectConnectionDetector(
      Dashboard instance, Provider<ConnectionDetector> connectionDetectorProvider) {
    instance.connectionDetector = connectionDetectorProvider.get();
  }

  public static void injectMediaPlayer(
      Dashboard instance, Provider<MediaPlayer> mediaPlayerProvider) {
    instance.mediaPlayer = mediaPlayerProvider.get();
  }

  public static void injectAudioManager(
      Dashboard instance, Provider<AudioManager> audioManagerProvider) {
    instance.audioManager = audioManagerProvider.get();
  }

  public static void injectUtils(Dashboard instance, Provider<Utilities> utilsProvider) {
    instance.utils = utilsProvider.get();
  }
}

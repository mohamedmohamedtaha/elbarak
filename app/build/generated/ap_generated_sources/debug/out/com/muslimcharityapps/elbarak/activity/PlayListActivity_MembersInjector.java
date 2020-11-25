package com.muslimcharityapps.elbarak.activity;

import android.content.SharedPreferences;
import com.muslimcharityapps.elbarak.utils.Utilities;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PlayListActivity_MembersInjector implements MembersInjector<PlayListActivity> {
  private final Provider<Utilities> utilsProvider;

  private final Provider<SharedPreferences> sharedpreferencesProvider;

  private final Provider<SharedPreferences.Editor> editorProvider;

  public PlayListActivity_MembersInjector(
      Provider<Utilities> utilsProvider,
      Provider<SharedPreferences> sharedpreferencesProvider,
      Provider<SharedPreferences.Editor> editorProvider) {
    assert utilsProvider != null;
    this.utilsProvider = utilsProvider;
    assert sharedpreferencesProvider != null;
    this.sharedpreferencesProvider = sharedpreferencesProvider;
    assert editorProvider != null;
    this.editorProvider = editorProvider;
  }

  public static MembersInjector<PlayListActivity> create(
      Provider<Utilities> utilsProvider,
      Provider<SharedPreferences> sharedpreferencesProvider,
      Provider<SharedPreferences.Editor> editorProvider) {
    return new PlayListActivity_MembersInjector(
        utilsProvider, sharedpreferencesProvider, editorProvider);
  }

  @Override
  public void injectMembers(PlayListActivity instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.utils = utilsProvider.get();
    instance.sharedpreferences = sharedpreferencesProvider.get();
    instance.editor = editorProvider.get();
  }

  public static void injectUtils(PlayListActivity instance, Provider<Utilities> utilsProvider) {
    instance.utils = utilsProvider.get();
  }

  public static void injectSharedpreferences(
      PlayListActivity instance, Provider<SharedPreferences> sharedpreferencesProvider) {
    instance.sharedpreferences = sharedpreferencesProvider.get();
  }

  public static void injectEditor(
      PlayListActivity instance, Provider<SharedPreferences.Editor> editorProvider) {
    instance.editor = editorProvider.get();
  }
}

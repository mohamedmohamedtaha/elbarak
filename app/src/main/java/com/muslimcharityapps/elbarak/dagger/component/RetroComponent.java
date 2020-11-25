package com.muslimcharityapps.elbarak.dagger.component;

import com.muslimcharityapps.elbarak.activity.Dashboard;
import com.muslimcharityapps.elbarak.activity.PlayListActivity;
import com.muslimcharityapps.elbarak.activity.PlayListFavorite;
import com.muslimcharityapps.elbarak.dagger.module.RetroModule;
import com.muslimcharityapps.elbarak.dagger.module.SharedPreferenceModule;
import com.muslimcharityapps.elbarak.dagger.module.UtilityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ssb on 11/3/17.
 */

@Singleton
@Component(modules = {RetroModule.class, SharedPreferenceModule.class, UtilityModule.class})
public interface RetroComponent {
    void inject(Dashboard dashboard);

    void inject(PlayListActivity playListActivity);

    void inject(PlayListFavorite playListFavorite);
}

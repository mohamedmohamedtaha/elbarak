// Generated code from Butter Knife. Do not modify!
package com.muslimcharityapps.elbarak.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.navigation.NavigationView;
import com.muslimcharityapps.elbarak.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Dashboard_ViewBinding implements Unbinder {
  private Dashboard target;

  private View view7f090051;

  @UiThread
  public Dashboard_ViewBinding(Dashboard target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Dashboard_ViewBinding(final Dashboard target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.songTitleLabel = Utils.findRequiredViewAsType(source, R.id.songTitle, "field 'songTitleLabel'", TextView.class);
    target.btnPlay = Utils.findRequiredViewAsType(source, R.id.btnPlay, "field 'btnPlay'", ImageButton.class);
    target.songTotalDurationLabel = Utils.findRequiredViewAsType(source, R.id.songTotalDurationLabel, "field 'songTotalDurationLabel'", TextView.class);
    target.songCurrentDurationLabel = Utils.findRequiredViewAsType(source, R.id.songCurrentDurationLabel, "field 'songCurrentDurationLabel'", TextView.class);
    target.songProgressBar = Utils.findRequiredViewAsType(source, R.id.songProgressBar, "field 'songProgressBar'", SeekBar.class);
    target.spinner = Utils.findRequiredViewAsType(source, R.id.progressBarSpinner, "field 'spinner'", ProgressBar.class);
    target.drawer = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'drawer'", DrawerLayout.class);
    target.navigationView = Utils.findRequiredViewAsType(source, R.id.nav_view, "field 'navigationView'", NavigationView.class);
    target.volumeSeekbar = Utils.findRequiredViewAsType(source, R.id.seekBarVolume, "field 'volumeSeekbar'", SeekBar.class);
    target.btnPrevious = Utils.findRequiredViewAsType(source, R.id.btnPrevious, "field 'btnPrevious'", ImageButton.class);
    target.btnRepeat = Utils.findRequiredViewAsType(source, R.id.btnRepeat, "field 'btnRepeat'", ImageButton.class);
    target.btnShuffle = Utils.findRequiredViewAsType(source, R.id.btnShuffle, "field 'btnShuffle'", ImageButton.class);
    target.btnNext = Utils.findRequiredViewAsType(source, R.id.btnNext, "field 'btnNext'", ImageButton.class);
    target.ivWeatherCondition = Utils.findRequiredViewAsType(source, R.id.ivWeatherCondition, "field 'ivWeatherCondition'", ImageView.class);
    target.tvWeatherTemperature = Utils.findRequiredViewAsType(source, R.id.tvWeatherTemperature, "field 'tvWeatherTemperature'", TextView.class);
    target.tvWeatherLocation = Utils.findRequiredViewAsType(source, R.id.tvWeatherLocation, "field 'tvWeatherLocation'", TextView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tvTime, "field 'tvTime'", TextView.class);
    target.tvAmPm = Utils.findRequiredViewAsType(source, R.id.tvAmPm, "field 'tvAmPm'", TextView.class);
    target.tvDate = Utils.findRequiredViewAsType(source, R.id.tvDate, "field 'tvDate'", TextView.class);
    target.btnWeatherByZipCode = Utils.findRequiredViewAsType(source, R.id.btnWeatherByZipCode, "field 'btnWeatherByZipCode'", Button.class);
    view = Utils.findRequiredView(source, R.id.btnFavorite, "field 'btnFavorite' and method 'setFavorite'");
    target.btnFavorite = Utils.castView(view, R.id.btnFavorite, "field 'btnFavorite'", ImageButton.class);
    view7f090051 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setFavorite();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    Dashboard target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.songTitleLabel = null;
    target.btnPlay = null;
    target.songTotalDurationLabel = null;
    target.songCurrentDurationLabel = null;
    target.songProgressBar = null;
    target.spinner = null;
    target.drawer = null;
    target.navigationView = null;
    target.volumeSeekbar = null;
    target.btnPrevious = null;
    target.btnRepeat = null;
    target.btnShuffle = null;
    target.btnNext = null;
    target.ivWeatherCondition = null;
    target.tvWeatherTemperature = null;
    target.tvWeatherLocation = null;
    target.tvTime = null;
    target.tvAmPm = null;
    target.tvDate = null;
    target.btnWeatherByZipCode = null;
    target.btnFavorite = null;

    view7f090051.setOnClickListener(null);
    view7f090051 = null;
  }
}

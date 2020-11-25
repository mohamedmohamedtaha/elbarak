package com.muslimcharityapps.elbarak.dagger.module;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;

import com.muslimcharityapps.elbarak.utils.ConnectionDetector;
import com.muslimcharityapps.elbarak.utils.Utilities;
import com.muslimcharityapps.elbarak.utils.Values;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ssb on 11/3/17.
 */

@Module
public class UtilityModule implements Values {

    private String TAG = UtilityModule.class.getSimpleName();
    private Application ssbApplication;

    public UtilityModule(Application ssbApplication) {
        this.ssbApplication = ssbApplication;
    }

    /*
    CONFIGURING CONNECTION DETECTOR
     */

    @Provides
    @Singleton
    ConnectionDetector provideConnectionDetector() {
        return new ConnectionDetector(ssbApplication);
    }

    /*
    CONFIGURING MEDIA PLAYER
     */

    @Provides
    @Singleton
    MediaPlayer provideMediaPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(ssbApplication.getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        return mediaPlayer;
    }

    /*
    CONFIGURING AUDIO MANAGER
     */

    @Provides
    @Singleton
    AudioManager provideAudioManager() {
        return (AudioManager) ssbApplication.getSystemService(Context.AUDIO_SERVICE);
    }

    /*
    CONFIGURING UTILITY
     */

    @Provides
    @Singleton
    Utilities provideUtilities() {
        return new Utilities();
    }
}

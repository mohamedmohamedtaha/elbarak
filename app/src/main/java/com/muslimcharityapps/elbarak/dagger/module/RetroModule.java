package com.muslimcharityapps.elbarak.dagger.module;

import android.app.Application;

import com.muslimcharityapps.elbarak.retro.RetroWS;
import com.muslimcharityapps.elbarak.utils.Values;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ssb on 11/3/17.
 */

@Module
public class RetroModule implements Values {

    private String TAG = RetroModule.class.getSimpleName();
    private Application ssbApplication;

    public RetroModule(Application ssbApplication) {
        this.ssbApplication = ssbApplication;
    }

    /*
    CONFIGURING RETROFIT SINGLETON
     */

    @Provides
    @Singleton
    Retrofit provideRetro() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient
                = new OkHttpClient
                .Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WS_SERVER_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }

    @Provides
    @Singleton
    RetroWS provideRetroWS(Retrofit retrofit) {
        return retrofit.create(RetroWS.class);
    }
}

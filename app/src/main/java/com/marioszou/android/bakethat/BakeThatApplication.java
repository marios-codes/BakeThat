package com.marioszou.android.bakethat;

import android.app.Application;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class BakeThatApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) {
      Timber.plant(new DebugTree());
    }
  }
}

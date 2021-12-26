package com.example.schedioapp

import android.app.Application
import timber.log.Timber

class SchedioApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Timber initialization
        Timber.plant(Timber.DebugTree())
    }
}
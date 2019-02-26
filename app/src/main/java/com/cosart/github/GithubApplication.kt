package com.cosart.github

import android.app.Application
import timber.log.Timber

class GithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.w("Tree planted")
        }
    }

    companion object {
        private lateinit var instance: GithubApplication
        fun get() = instance
    }
}
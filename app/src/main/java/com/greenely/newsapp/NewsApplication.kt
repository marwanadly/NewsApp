package com.greenely.newsapp

import android.app.Application
import com.greenely.newsapp.di.components.ApplicationComponent
import com.greenely.newsapp.di.components.DaggerApplicationComponent
import com.greenely.newsapp.di.modules.ApplicationModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class NewsApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        component.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}
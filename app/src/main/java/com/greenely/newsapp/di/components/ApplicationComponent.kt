package com.greenely.newsapp.di.components

import android.content.Context
import com.greenely.newsapp.NewsApplication
import com.greenely.newsapp.data.NewsDataManager
import com.greenely.newsapp.di.modules.ApplicationModule
import com.greenely.newsapp.di.scope.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: NewsApplication)
    fun getAppContext(): Context
    fun getNewsDataManager(): NewsDataManager
}
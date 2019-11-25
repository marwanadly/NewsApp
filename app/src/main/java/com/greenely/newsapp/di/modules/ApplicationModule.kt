package com.greenely.newsapp.di.modules

import android.content.Context

import com.greenely.newsapp.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides



@Module(includes = [NetworkModule::class, DataModule::class])
class ApplicationModule(context: Context) {

    private var mContext: Context = context

    @Provides
    @ApplicationScope
    fun provideContext(): Context {
        return mContext
    }
}
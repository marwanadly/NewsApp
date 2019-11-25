package com.greenely.newsapp.di.modules

import com.greenely.newsapp.api.NewsApi
import com.greenely.newsapp.data.NewsDataManager
import com.greenely.newsapp.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    @ApplicationScope
    fun providesNewsDataManager(newsApi: NewsApi): NewsDataManager {
        return NewsDataManager(newsApi)
    }
}
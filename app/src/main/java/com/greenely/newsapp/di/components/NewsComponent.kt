package com.greenely.newsapp.di.components

import com.greenely.newsapp.di.scope.ActivityScope
import com.greenely.newsapp.ui.NewsActivity
import dagger.Component

@Component(dependencies = [ApplicationComponent::class])
@ActivityScope
interface NewsComponent {
    fun inject(newsActivity: NewsActivity)

}
package com.greenely.newsapp.data

import com.greenely.newsapp.BuildConfig
import com.greenely.newsapp.api.NewsApi
import com.greenely.newsapp.data.model.NewsObject
import io.reactivex.Observable
import javax.inject.Inject

class NewsDataManager @Inject constructor(private val newsApi: NewsApi) {

    fun getNews(pageNumber: Int): Observable<NewsObject> =
        newsApi.getNews(
            apiKey = BuildConfig.API_KEY,
            query = "android",
            page = pageNumber,
            pageSize = 10,
            sortBy = "publishedAt",
            language = "en"
        )

}
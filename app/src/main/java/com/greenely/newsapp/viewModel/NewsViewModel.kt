package com.greenely.newsapp.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greenely.newsapp.BuildConfig
import com.greenely.newsapp.data.NewsDataManager
import com.greenely.newsapp.data.model.Article
import io.reactivex.Scheduler
import timber.log.Timber
import javax.inject.Inject


class NewsViewModel @Inject constructor(private val newsDataManager: NewsDataManager) {

    private lateinit var ioScheduler: Scheduler
    private lateinit var uiScheduler: Scheduler

    private var newsLiveData: MutableLiveData<List<Article>> = MutableLiveData()


    fun setRxSchedulers(io: Scheduler, ui: Scheduler) {
        ioScheduler = io
        uiScheduler = ui
    }

    @SuppressLint("CheckResult")
    fun getNews(pageNumber: Int) {
        newsDataManager.getNews(pageNumber)
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe({ news ->
                newsLiveData.value = news.newsList
            },
                { error ->
                    Timber.e(error)
                })
    }

    fun getNewsLiveData(): LiveData<List<Article>> = newsLiveData
}
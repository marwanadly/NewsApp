package com.greenely.newsapp.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.greenely.newsapp.data.NewsDataManager
import com.greenely.newsapp.data.model.Article
import com.greenely.newsapp.data.model.NewsObject
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

class NewsViewModelTest {

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Mock
    lateinit var newsDataManager: NewsDataManager

    @Mock
    lateinit var newsLiveDataObserver: Observer<List<Article>>

    @Mock
    lateinit var newsViewModel: NewsViewModel

    @Mock
    lateinit var mockNewsObject: NewsObject


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsViewModel = NewsViewModel(newsDataManager)
        newsViewModel.setRxSchedulers(Schedulers.trampoline(), Schedulers.trampoline())
        newsViewModel.getNewsLiveData().observeForever(newsLiveDataObserver)
    }

    @Test
    fun `Get news when pageNumber is 1`() {
        whenever(newsDataManager.getNews(1)).thenReturn(Observable.just(mockNewsObject))
        newsViewModel.getNews(1)
        verify(newsLiveDataObserver, times(1)).onChanged(mockNewsObject.newsList)
    }

    @Test
    fun `Error when trying to get more than 100 news`() {
        val errorResponse = Response.error<NewsObject>(426, ResponseBody.create(null, ""))
        whenever(newsDataManager.getNews(11)).thenReturn(Observable.error(HttpException(errorResponse)))
        newsViewModel.getNews(11)
        verify(newsLiveDataObserver, times(0)).onChanged(mockNewsObject.newsList)
    }

    @Test
    fun `Error when trying to get news due to internet problem`() {
        val errorResponse = SocketTimeoutException()
        whenever(newsDataManager.getNews(1)).thenReturn(Observable.error(errorResponse))
        newsViewModel.getNews(1)
        verify(newsLiveDataObserver, times(0)).onChanged(mockNewsObject.newsList)
    }
}
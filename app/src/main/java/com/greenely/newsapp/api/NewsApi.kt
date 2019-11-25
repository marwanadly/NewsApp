package com.greenely.newsapp.api

import com.greenely.newsapp.data.model.NewsObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    fun getNews(@Query("apiKey") apiKey:String,
                @Query("qInTitle") query:String,
                @Query("pageSize") pageSize: Int,
                @Query("page") page: Int,
                @Query("sortBy") sortBy: String,
                @Query("language") language: String): Observable<NewsObject>
}
package com.greenely.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class NewsObject(
    @SerializedName("articles")
    var newsList: List<Article>
)
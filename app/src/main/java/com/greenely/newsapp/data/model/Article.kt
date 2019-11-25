package com.greenely.newsapp.data.model

import com.google.gson.annotations.SerializedName
import com.greenely.newsapp.util.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

data class Article(
    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("urlToImage")
    var imageUrl: String,

    @SerializedName("publishedAt")
    var publishedAt: String,

    @SerializedName("url")
    var newsUrl: String

) {
    fun getArticleTimeFormated(): String {
        val calendar = Calendar.getInstance()
        calendar.time =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(publishedAt)
        return DateTimeFormatter.prettyTime.format(calendar)
    }
}
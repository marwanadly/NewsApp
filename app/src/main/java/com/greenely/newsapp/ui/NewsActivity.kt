package com.greenely.newsapp.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenely.newsapp.NewsApplication
import com.greenely.newsapp.R
import com.greenely.newsapp.di.components.ApplicationComponent
import com.greenely.newsapp.di.components.DaggerNewsComponent
import com.greenely.newsapp.ui.adapter.NewsAdapter
import com.greenely.newsapp.ui.interfaces.OnNewsClick
import com.greenely.newsapp.util.EndlessScrollListener
import com.greenely.newsapp.viewModel.NewsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class NewsActivity : AppCompatActivity(), OnNewsClick {

    @Inject
    lateinit var newsViewModel: NewsViewModel

    var pageNumber = 1

    private fun getApplicationComponent(): ApplicationComponent =
        (application as NewsApplication).component


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerNewsComponent.builder().applicationComponent(getApplicationComponent())
            .build().inject(this)


        newsViewModel.setRxSchedulers(Schedulers.io(), AndroidSchedulers.mainThread())
        setupNewsAdapter()
        newsViewModel.getNews(pageNumber = pageNumber)
    }

    private fun setupNewsAdapter() {

        progressBar.visibility = View.VISIBLE
        val newsAdapter = NewsAdapter(this)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = newsAdapter
        newsViewModel.getNewsLiveData().observeForever {

            if (progressBar.visibility == View.VISIBLE) progressBar.visibility = View.GONE
            newsAdapter.addAll(it)
            pageNumber += 1
        }

        newsRecyclerView.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                newsViewModel.getNews(pageNumber = pageNumber)
            }
        })
    }

    override fun onNewsClick(newsUrl: String?) {
        if (newsUrl != null) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(newsUrl)))
        } else {
            Toast.makeText(this, "There is no reference for this news", Toast.LENGTH_SHORT).show()
        }
    }
}

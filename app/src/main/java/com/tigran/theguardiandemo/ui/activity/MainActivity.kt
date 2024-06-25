package com.tigran.theguardiandemo.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tigran.domain.model.Article
import com.tigran.theguardiandemo.R
import com.tigran.theguardiandemo.databinding.ActivityMainBinding
import com.tigran.theguardiandemo.ui.adapter.ArticleAdapter
import com.tigran.theguardiandemo.ui.adapter.PaginationScrollListener
import com.tigran.theguardiandemo.ui.fragment.ArticleDetailedFragment
import com.tigran.theguardiandemo.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        setupSearchView()
        setupRV()

        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.articles.collect { articles ->
                hideLoading()
                submitList(articles)
            }
        }
        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.error.collect {
                showErrorToast()
            }
        }
        showLoading()
        mainViewModel.requestArticleData(page = 1)
    }

    private fun setupRV() {
        binding?.run {
            val adapter = ArticleAdapter { article ->
                navigateToArticleDetail(article)
            }
            val layoutManager =
                LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            rvArticles.layoutManager = layoutManager
            rvArticles.adapter = adapter
            rvArticles.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
                override fun loadMoreItems() {
                    mainViewModel.requestArticleData(
                        (layoutManager.itemCount / 10 + 1)
                    )
                }

                override fun isLoading(): Boolean = pbLoading.isVisible
            })
        }
    }

    private fun showErrorToast() {
        Toast.makeText(
            applicationContext, "no network, trying  to load from cache", Toast.LENGTH_SHORT
        ).show()
    }

    private fun submitList(articles: List<Article>?) {
        articles?.takeIf { it.isNotEmpty() }?.let {
            (binding?.rvArticles?.adapter as? ArticleAdapter)?.addData(it)
        }
    }

    private fun setupSearchView() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { keyword ->
                    showLoading()
                    submitList(emptyList())
                    mainViewModel.updateSearchKeyword(keyword)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun showLoading() {
        binding?.pbLoading?.isVisible = true
    }

    private fun hideLoading() {
        binding?.pbLoading?.isVisible = false
    }

    private fun navigateToArticleDetail(article: Article) {
        mainViewModel.cacheArticle(article) {
            val openingFragment = ArticleDetailedFragment.newInstance(article.id)
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, openingFragment)
                .addToBackStack(null).commit()
        }
    }
}
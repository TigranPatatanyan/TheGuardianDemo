package com.tigran.theguardiandemo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tigran.theguardiandemo.R
import com.tigran.theguardiandemo.viewmodel.ArticleDetailedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class ArticleDetailedFragment : Fragment() {

    private val viewModel: ArticleDetailedViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.article.onEach {
        }.flowOn(Dispatchers.Main)
        arguments?.getInt(ARTICLE_ID_KEY)?.let { viewModel.requestData(it) }
    }

    companion object {
        const val ARTICLE_ID_KEY = "ARTICLE_ID_KEY"
        fun newInstance(articleID: Int): ArticleDetailedFragment {
            val args = Bundle()
            args.putInt(ARTICLE_ID_KEY, articleID)
            return ArticleDetailedFragment().apply {
                arguments = args
            }
        }
    }
}
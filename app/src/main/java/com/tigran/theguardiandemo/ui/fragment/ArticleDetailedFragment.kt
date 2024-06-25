package com.tigran.theguardiandemo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tigran.theguardiandemo.databinding.FragmentDetailedBinding
import com.tigran.theguardiandemo.viewmodel.ArticleDetailedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class ArticleDetailedFragment : Fragment() {

    private val viewModel: ArticleDetailedViewModel by viewModel()
    private var mBinding: FragmentDetailedBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDetailedBinding.inflate(layoutInflater)
        val view = mBinding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.article.onEach {
            mBinding?.let { binding ->
                Glide.with(binding.root.context).load(it.url).into(binding.ivContentImage)
                binding.tvContentTitle.text = it.title
                binding.tvContentDescription.text = it.bodyText
            }
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
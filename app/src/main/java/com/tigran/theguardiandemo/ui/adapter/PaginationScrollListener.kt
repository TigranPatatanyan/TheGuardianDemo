package com.tigran.theguardiandemo.ui.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) { // check for scroll down
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

            if (!isLoading()) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    loadMoreItems()
                }
            }
        }
    }

    abstract fun loadMoreItems()
    abstract fun isLoading(): Boolean
}
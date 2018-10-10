package com.android.demo.kotlinvm.ui.main.fragment

import android.arch.lifecycle.Observer
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.baselibrary.base.BaseNoRefreshRvListFragment
import com.android.baselibrary.utils.startActivity
import com.android.demo.kotlinvm.ui.category.CategoryDetailActivity
import com.android.demo.kotlinvm.ui.main.MainActivity
import com.android.demo.kotlinvm.ui.main.adapter.CategoryAdapter
import com.android.demo.kotlinvm.ui.main.model.bean.CategoryBean
import com.android.demo.kotlinvm.ui.main.viewmodel.DiscoveryViewModel
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Des:  分类
 */
class DiscoveryCategoryFragment : BaseNoRefreshRvListFragment<DiscoveryViewModel, CategoryBean>() {

    companion object {
        fun newInstance(): DiscoveryCategoryFragment {
            return DiscoveryCategoryFragment()
        }
    }

    override fun isLoadMore(): Boolean = false

    override fun getListAdapter(): BaseQuickAdapter<CategoryBean, *> = CategoryAdapter(null)


    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 2)
    }

    override fun initView() {
        super.initView()
        mViewModel.getCategoryBeanLiveData().observe(this, Observer {
            mBaseAdapter.setNewData(it)
        })
        mBaseAdapter.setOnItemClickListener { adapter, _, position ->
            startActivity<CategoryDetailActivity>(CategoryDetailActivity.KEY_CATEGORY_DATA to adapter.getItem(position))
        }
    }

    override fun getContentData() {
        mViewModel.getCategoryData()

    }


}
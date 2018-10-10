# KotlinVM
在开源项目[KotlinMvp](https://xuhaoblog.com/KotlinMvp/)的基础上，结合ViewModel +LiveData的优点同时抛弃MVP中需要繁琐的接口约定、过度封装导致不容易读懂。<br/>
## 功能特色
* 精简读懂：Activity 或Fragment直接监听 Controller层实现UI更新相关操作，不需要通过实现接口才操作UI，Controller层也不需要担心内存泄漏等问<br/>






* 容易上手：直接调用mViewModel里的函数，不需要关心生命周期导致的各种问题，因为ViewModel +LiveData 已经很好的解决一切例如需要实现RxLifecycle才能解决的问题<br/>


* 方便快捷：Activity 或Fragment跟Controller层之间通过监听实现分离，所以在Base类实现之间的约定很灵活，基本只要实现业务逻辑就能完成列表功能<br/>

### 示例
在[GankAndroidActivity](https://github.com/luechenying/KotlinVM/blob/master/app/src/main/java/com/android/demo/kotlinvm/ui/gank/GankAndroidActivity.kt)中只需重写这两个方法
```
    override fun getContentData() {
        mViewModel.getGankData("Android")
    }

    override fun getMoreData() {
        mViewModel.loadMoreGankData("Android")
    }
```
且实现逻辑返回的监听
```
mViewModel.getmGankItemsLiveData().observe(this, Observer { mBaseAdapter.setNewData(it) })
mViewModel.getMoremGankItemsLiveData().observe(this, Observer { it?.let { mBaseAdapter.addData(it) } })
```
在[CategoryDetailViewModel](https://github.com/luechenying/KotlinVM/blob/master/app/src/main/java/com/android/demo/kotlinvm/ui/category/viewmodel/CategoryDetailViewModel.kt)中只需实现具体逻辑就能在界面展现，而且完全绑定生命周期
```
    /**
     * 获取分类详情的列表信息
     */
    fun getCategoryDetailList(id: Long) {
        //可以进入界面不用loading
        mCategoryDetailModel.getCategoryDetailList(id)
                .subscribe({ issue ->

                    //设置首次加载数据的
                    mCateDetailsLiveData.value = issue.itemList

                    //  设置下一页URL
                    if (issue.nextPageUrl.isNullOrEmpty()) {
                        //没有更多
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW
                    } else {
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW

                        mNextPageLiveData.value = issue.nextPageUrl
                    }

                }, {
                    mRequestTypeLiveData.value = RequestType.TYPE_NET_ERROR
                    mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW
                }).bindToLifecycle()
    }

```

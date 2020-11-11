# PagingAdapter
  Paging3 RecyclerView Adapter

## 超简单易用的使用Paging3封装的RecyclerView的列表适配器

### 本库的优势：

- 使用paging3封装，具有paging3完全优势，快速实现列表分页，无缝加载；
- 可以对条目实现增删改，弥补paging无法修改数据问题；
- 不需要实现paging的数据库缓存，直接网络请求即可；
- 多类型条目解耦，新增新的条目完全不需要修改原有代码，直接新建holder即可；
- 已经完全不需要再自己实现adapter了；
- holder对UI的操作全由helper实现链式操作，不用再绑定viewId了；



## 使用步骤：


### 依赖方式：
1. 项目的根build.gradle里添加jitpack仓库
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2. 依赖：在模块build.gradle添加
```
dependencies {
	    implementation 'com.github.jarryleo:PagingAdapter:1.0.1'
}
```

### 代码使用：

#### 1. 数据模型bean类实现接口 DifferData
```
//用作数据比较，执行RecyclerView条目动画，方法可以不实现
interface DifferData {
    fun areItemsTheSame(d: DifferData): Boolean
    fun areContentsTheSame(d: DifferData): Boolean
    fun getChangePayload(d: DifferData): Any?
}
```
#### 2. 实现条目holder：继承SimpleHolder
```
class NewsHolder : 继承SimpleHolder<NewsBean.StoriesBean>() {

    override fun getLayoutRes(): Int {
        return R.layout.item_news
    }

    override fun bindData(
        helper: PagingDataAdapterKtx.ItemHelper,
        data: NewsBean.StoriesBean?,
        payloads: MutableList<Any>?
    ) {
        if (data == null) return
        helper.setText(R.id.tv_title, data.title)
            .findViewById<ImageView>(R.id.iv_cover)
            .loadImage(data.images?.get(0) ?: "", corners = 6.dp)
    }
}
```
#### 3. 设置Adapter
```
 recyclerView.adapter = SimplePagingAdapter(NewsHolder())

```

`多条目类型就实现多个holder，直接传给SimplePagingAdapter的构造即可，构造支持可变参数`


#### 4. 给adapter设置数据

```
//在ViewModel类里面实现
val pager = SimplePager<Long, DifferData>(viewModelScope) {
        val date = it.key ?: initialKey
        try {
            //从网络获取数据
            val data = api.getNews(date).await()
            //返回数据
            PagingSource.LoadResult.Page(data.stories, null, data.date?.toLongOrNull())
        } catch (e: Exception) {
            //请求失败
            PagingSource.LoadResult.Error(e)
        }
    }


//绑定数据源
adapter.setPager(model.pager)
```


**使用就是这么方便，其中一些定义需要对paging3有一定了解**



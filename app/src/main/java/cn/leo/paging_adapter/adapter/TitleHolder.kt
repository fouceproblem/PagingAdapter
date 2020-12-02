package cn.leo.paging_adapter.adapter

import cn.leo.paging_adapter.R
import cn.leo.paging_adapter.bean.TitleBean
import cn.leo.paging_ktx.ItemHelper
import cn.leo.paging_ktx.SimpleHolder

/**
 * @author : leo
 * @date : 2020/11/10
 * @description : 标题holder
 */
class TitleHolder : SimpleHolder<TitleBean>(R.layout.item_title) {
    override fun bindItem(helper: ItemHelper, data: TitleBean, payloads: MutableList<Any>?) {
        helper.setText(R.id.tv_title, data.title)
    }
}
package com.example.myapplication.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.bean.MultiUser

class MultiUserAdapter(data: MutableList<MultiUser>? = null) :
    BaseMultiItemQuickAdapter<MultiUser, BaseViewHolder>(data) {

    init {
        // 添加布局类型
        addItemType(1, R.layout.item_text) // 文本类型
        addItemType(2, R.layout.item_image) // 图片类型
    }

    override fun convert(holder: BaseViewHolder, item: MultiUser) {
        when (holder.itemViewType) {
            1 -> { // 文本类型
                holder.setText(R.id.tv_text, item.name)
            }
            2 -> { // 图片类型
                holder.setImageResource(R.id.iv_image, R.drawable.ic_launcher_background)
            }
        }
    }
}
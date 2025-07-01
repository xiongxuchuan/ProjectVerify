package com.example.myapplication.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.bean.User

class UserAdapter(data: MutableList<User>? = null) :
    BaseQuickAdapter<User, BaseViewHolder>(R.layout.item_user, data) {

    override fun convert(holder: BaseViewHolder, item: User) {
        // 绑定数据到视图
        holder.setText(R.id.tv_name, item.name)
            .setText(R.id.tv_age, "${item.age}岁")
            .setImageResource(R.id.iv_avatar, item.avatar)

    }
}
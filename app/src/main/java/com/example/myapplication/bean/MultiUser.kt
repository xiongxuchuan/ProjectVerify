package com.example.myapplication.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * 多布局实现：首先让数据类实现 MultiItemEntity 接口
 *
 * */
data class MultiUser(
    val id: Int,
    val name: String,
    override val itemType: Int
) : MultiItemEntity {
}
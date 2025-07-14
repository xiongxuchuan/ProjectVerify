package com.example.myapplication.bean

data class ChartData(
    val indexName: String,
    val time: String, // 建议解析为时间戳
    val indexValue: Int,
    val unit: String,
    val extra: Int? // extra为null或1时处理不同逻辑
)

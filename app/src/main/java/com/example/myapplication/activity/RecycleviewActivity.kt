package com.example.myapplication.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.WaterfallAdapter
import com.example.myapplication.bean.WaterfallItem

/**
 * 瀑布流布局实现
 *
 */
class RecycleviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycleview)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // 创建两列的瀑布流布局，垂直方向
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        // 创建数据集
        val items = mutableListOf<WaterfallItem>()
        for (i in 1..20) {
            // 随机决定哪些条目高度占两行
            val isDoubleHeight = i % 3 == 0
            items.add(WaterfallItem("Item $i", isDoubleHeight))
        }

        // 设置适配器
        val adapter = WaterfallAdapter(items)
        recyclerView.adapter = adapter
    }
}
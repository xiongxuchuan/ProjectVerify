package com.example.myapplication.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.MultiUserAdapter
import com.example.myapplication.bean.MultiUser

class MultiAdapterHelperActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:MultiUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycleview)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var userList = mutableListOf<MultiUser>().apply {
            add(MultiUser(1, "张三", 2))
            add(MultiUser(2, "李四", 1))
            add(MultiUser(3, "王五", 1))
        }
        adapter = MultiUserAdapter(userList)
        recyclerView.adapter = adapter
    }
}
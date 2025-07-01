package com.example.myapplication.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.myapplication.R
import com.example.myapplication.adapter.UserAdapter
import com.example.myapplication.bean.User

class AdapterHelperActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycleview)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 初始化数据
        val userList = mutableListOf<User>().apply {
            add(User(1, "张三", 25, android.R.drawable.ic_menu_add))
            add(User(2, "李四", 30, android.R.drawable.ic_input_add))
            add(User(3, "王五", 28, android.R.drawable.star_on))
            // 添加更多数据...
        }

        adapter = UserAdapter(userList)
        recyclerView.adapter = adapter

        // 设置动画效果
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)

        // 设置点击事件
        adapter.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(this, "点击了 ${userList[position].name}", Toast.LENGTH_SHORT).show()
        }

        // 设置子项点击事件
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.iv_avatar -> {
                    Toast.makeText(this, "点击了 ${userList[position].name} 的头像", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 设置长按事件
        adapter.setOnItemLongClickListener { adapter, view, position ->
            Toast.makeText(this, "长按了 ${userList[position].name}", Toast.LENGTH_SHORT).show()
            true
        }
    }
}
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.databinding.MainLayoutBinding
import com.example.myapplication.ui.theme.MyApplicationTheme

/**
 * viewbinding的使用
 */
class MainActivity : ComponentActivity() {
    // 根据
    private lateinit var binding: MainLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView.text = "Hello world"

        funLazy()
    }
}

fun funLazy() {
    println("Before accessing myLazyProperty")
    println(myLazyProperty)
    println("After accessing myLazyProperty")
    println(myLazyProperty) // 第二次访问，不会再次初始化
}

fun funPair() {
    // Pair 是一个泛型类，用于表示两个不可变值的简单组合
    var disconnectDeviceInfo: Pair<String, Int> = Pair("", 0)
}

// by lazy线程安全，通过委托属性的方式，实现了只在第一次访问时才进行初始化的功能。它在需要避免昂贵的初始化操作，或者依赖其他变量的初始化场景下非常有用
val myLazyProperty: String by lazy {
    println("Initializing myLazyProperty")
    "This is the value of myLazyProperty"
}
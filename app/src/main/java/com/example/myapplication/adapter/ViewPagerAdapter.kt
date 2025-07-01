package com.example.myapplication.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.fragment.TabFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabFragment.newInstance("标签页1的内容")
            1 -> TabFragment.newInstance("标签页2的内容")
            2 -> TabFragment.newInstance("标签页3的内容")
            else -> throw IllegalArgumentException("无效位置: $position")
        }
    }
}
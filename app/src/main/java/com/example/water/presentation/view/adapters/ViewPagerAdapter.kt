package com.example.water.presentation.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.water.presentation.view.LocationFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        const val MAP_TAB = 2
    }

    override fun getItemCount(): Int = MAP_TAB

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> LocationFragment.newInstance(59.950015F, 30.316599F)
        else -> LocationFragment.newInstance(55.753913F, 37.620836F)
    }
}
package com.example.cutenessoverload.utils

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val viewPagerFragment: Fragment,
    private val createFragmentFunc: (Int) -> Fragment,
    private val page_count: Int
) : FragmentStateAdapter(viewPagerFragment) {
    override fun getItemCount(): Int = page_count

    override fun createFragment(position: Int): Fragment =
        createFragmentFunc(position)
}
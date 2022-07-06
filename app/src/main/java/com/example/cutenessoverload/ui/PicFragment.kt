package com.example.cutenessoverload.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cutenessoverload.databinding.FragmentViewPagerBinding
import com.example.cutenessoverload.utils.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class PicFragment : Fragment() {

    private val binding by lazy { FragmentViewPagerBinding.inflate(layoutInflater) }

    fun viewPagerSwitcher(position: Int) : Fragment =
        when (position) {
            1 -> CatPicFragment()
            2 -> DogPicFragment()
            3 -> DuckPicFragment()
            4 -> FoxPicFragment()
            else -> AllPicFragment() // This includes 0
        }
    // These are parallel to the fragments in viewPagerSwitcher()
    private val pageNames = listOf(
        "All",
        "Cats",
        "Dogs",
        "Ducks",
        "Fox"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.viewPager.adapter =
            ViewPagerAdapter(
                this,
                ::viewPagerSwitcher,
                pageNames.size)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, pos -> tab.text = pageNames[pos] }.attach()
    }

}
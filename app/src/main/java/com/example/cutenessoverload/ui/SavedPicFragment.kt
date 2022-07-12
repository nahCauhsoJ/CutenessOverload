package com.example.cutenessoverload.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.cutenessoverload.databinding.FragmentViewPagerBinding
import com.example.cutenessoverload.ui.picfragment.*
import com.example.cutenessoverload.ui.savedpicfragment.*
import com.example.cutenessoverload.utils.ViewPagerAdapter
import com.example.cutenessoverload.viewmodel.CuteViewModel
import com.google.android.material.tabs.TabLayoutMediator

class SavedPicFragment : Fragment() {
    private val binding by lazy { FragmentViewPagerBinding.inflate(layoutInflater) }
    private val viewModel: CuteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.viewPager.adapter =
            ViewPagerAdapter(
                this,
                ::viewPagerSwitcher,
                PicFragment.pageNames.size)

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.changePage(position)
                }
            }
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, pos -> tab.text = PicFragment.pageNames[pos] }.attach()
    }

    private fun viewPagerSwitcher(position: Int) : Fragment =
        when (position) {
            1 -> CatSavedPicFragment()
            2 -> DogSavedPicFragment()
            3 -> DuckSavedPicFragment()
            4 -> FoxSavedPicFragment()
            else -> AllSavedPicFragment() // This includes 0
        }

    companion object {
        // These are parallel to the fragments in viewPagerSwitcher()
        val pageNames = listOf(
            "All",
            "Cats",
            "Dogs",
            "Ducks",
            "Fox"
        )
    }
}
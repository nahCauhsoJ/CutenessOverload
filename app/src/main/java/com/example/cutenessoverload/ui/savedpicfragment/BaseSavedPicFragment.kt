package com.example.cutenessoverload.ui.savedpicfragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.cutenessoverload.R
import com.example.cutenessoverload.databinding.FragmentSavedPicBinding
import com.example.cutenessoverload.model.CuteGeneric
import com.example.cutenessoverload.utils.LocalRequestState
import com.example.cutenessoverload.utils.SavedPicAdapter
import com.example.cutenessoverload.viewmodel.CuteViewModel

abstract class BaseSavedPicFragment: Fragment() {
    private val binding by lazy { FragmentSavedPicBinding.inflate(layoutInflater) }
    protected val viewModel: CuteViewModel by activityViewModels()
    private val savedPicAdapter by lazy {
        SavedPicAdapter() {
            if (viewModel.unsaveMode.value == true) return@SavedPicAdapter viewModel.unsaveDataClick(it)
            expandPic(it)
            return@SavedPicAdapter null
        }
    }

    // BasePicFragment has something identical.
    protected open var pageId: Int = -69
    protected open var requesterId: String = ""

    private lateinit var optionsMenu : MenuProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.savedPicListView.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = savedPicAdapter
        }

        binding.savedPicExpandLayout.setOnClickListener {
            it.visibility = View.INVISIBLE
        }

        viewModel.savedData.observe(viewLifecycleOwner) {
            when (it) {
                is LocalRequestState.ERROR -> ""
                LocalRequestState.PENDING -> ""
                is LocalRequestState.SUCCESS -> {
                    it.let {
                        if (requesterId == it.response.first) {
                            savedPicAdapter.refreshList(it.response.second)
                        }
                    }
                }
            }
        }

        getLocalData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var menuUnsaveConfirm: MenuItem? = null
        optionsMenu = object : MenuProvider{
            private lateinit var menu_unsave_pic: MenuItem
            private lateinit var menu_unsave_cancel: MenuItem
            private lateinit var menu_unsave_confirm: MenuItem

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.saved_pics_menu, menu)
                menu_unsave_pic = menu.findItem(R.id.menu_unsave_pic)
                menu_unsave_cancel = menu.findItem(R.id.menu_unsave_cancel)
                menu_unsave_confirm = menu.findItem(R.id.menu_unsave_confirm)
                menuUnsaveConfirm = menu_unsave_confirm
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
                when (menuItem.itemId) {
                    R.id.menu_unsave_pic -> {
                        binding.savedPicLayout.setBackgroundColor(Color.parseColor("#ff0000"))
                        menu_unsave_pic.isVisible = false
                        menu_unsave_cancel.isVisible = true
                        menu_unsave_confirm.isVisible = false
                        viewModel.toggleUnsaveMode(true)
                        true
                    }
                    R.id.menu_unsave_cancel -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Cancel")
                            .setMessage("You're about to discard changes...")
                            .setNegativeButton("Nevermind", null)
                            .setPositiveButton("Do it") { _, _ -> confirmCancel()}
                            .show()
                        true
                    }
                    R.id.menu_unsave_confirm -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Unsave")
                            .setMessage("You're about to unsave some cute pictures...")
                            .setNegativeButton("Nevermind", null)
                            .setPositiveButton("Do it") { _, _ -> confirmConfirm()}
                            .show()
                        true
                    }
                    else -> false
                }

            private fun confirmCancel() {
                binding.savedPicLayout.background = null
                menu_unsave_pic.isVisible = true
                menu_unsave_cancel.isVisible = false
                menu_unsave_confirm.isVisible = false
                viewModel.confirmCancelUnsave()
                viewModel.toggleUnsaveMode(false)
                savedPicAdapter.clearUnsaveIndicator()
            }

            private fun confirmConfirm() {
                menu_unsave_pic.isVisible = true
                menu_unsave_cancel.isVisible = false
                menu_unsave_confirm.isVisible = false
                viewModel.unsaveList.value?.let {
                    savedPicAdapter.deleteItems(it)
                }
                viewModel.confirmUnsave()
                viewModel.toggleUnsaveMode(false)
            }
        }

        requireActivity().addMenuProvider(optionsMenu)

        viewModel.unsaveList.observe(viewLifecycleOwner) {
            if (viewModel.unsaveMode.value!!)
                menuUnsaveConfirm?.isVisible = it.isNotEmpty()
        }
    }

    protected abstract fun getLocalData()

    private fun expandPic(cuteGeneric: CuteGeneric) {
        binding.savedPicExpandLayout.visibility = View.VISIBLE
        binding.savedPicExpandPic.setImageDrawable(null)
        Glide.with(binding.root)
            .load(cuteGeneric.image_url)
            .into(binding.savedPicExpandPic)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().removeMenuProvider(optionsMenu)
    }
}
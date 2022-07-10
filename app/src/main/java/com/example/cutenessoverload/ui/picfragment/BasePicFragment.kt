package com.example.cutenessoverload.ui.picfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.cutenessoverload.databinding.FragmentPicBinding
import com.example.cutenessoverload.repository.RequestState
import com.example.cutenessoverload.viewmodel.CuteViewModel

abstract class BasePicFragment : Fragment() {
    private val binding by lazy { FragmentPicBinding.inflate(layoutInflater) }
    protected val viewModel: CuteViewModel by activityViewModels()

    // The derived classes will set this.
    // Take reference from PicFragment for the correct ID
    protected open var pageId: Int = -69

    private var requestingUrl: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.cutePicRefresh.setOnClickListener {
            refreshPage()
        }

        binding.cutePicSave.setOnClickListener {

        }

        viewModel.currentPage.observe(viewLifecycleOwner) {
            it?.let {
                if (it.second == pageId) refreshPage()
            }
        }

        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.ERROR -> {
                    loadPicUI(true)
                }
                is RequestState.PENDING -> {
                    loadPicUI(false)
                }
                is RequestState.SUCCESS ->
                {
                    loadPicUI(true)

                    viewModel.currentPage.value?.let { page ->
                        if (page.second == pageId) {
                            viewModel.hasImage(it.response.image_url)
                            requestingUrl = it.response.image_url
                        }
                    }
                }
            }
        }

        // This is what loads the image whenever requested.
        // It becomes async because if the random URL received was actually
        //      displaying the same image that the user previously saved,
        //      the Save button needs to change to an Unsave button.
        viewModel.hasImageResult.observe(viewLifecycleOwner) { result ->
            result?.let {
                Glide.with(this)
                    .load(requestingUrl)
                    .into(binding.cutePicImage)
                setSaveButtonMode(it)
            }
        }

        return binding.root
    }

    protected abstract fun refreshPage()

    private fun setSaveButtonMode(is_unsave: Boolean) {
        if (is_unsave) binding.cutePicSave.text = "Unsave"
        else binding.cutePicSave.text = "Save"
    }

    private fun loadPicUI(done_loading: Boolean) {
        binding.cutePicRefresh.isEnabled = done_loading
        binding.cutePicSave.isEnabled = done_loading
        binding.picLoading.visibility = if (done_loading) View.INVISIBLE else View.VISIBLE
    }
}
package com.example.cutenessoverload.ui.picfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.cutenessoverload.databinding.FragmentPicBinding
import com.example.cutenessoverload.model.CuteGeneric
import com.example.cutenessoverload.utils.RequestState
import com.example.cutenessoverload.viewmodel.CuteViewModel

abstract class BasePicFragment : Fragment() {
    private val binding by lazy { FragmentPicBinding.inflate(layoutInflater) }
    protected val viewModel: CuteViewModel by activityViewModels()

    // The derived classes will set this.
    // Take reference from PicFragment for the correct ID
    protected open var pageId: Int = -69
    private var isCurrentPage: Boolean = false

    private var requestingUrl: String = ""
    private lateinit var responseData: CuteGeneric

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.cutePicRefresh.setOnClickListener {
            refreshPage()
        }

        binding.cutePicSave.setOnClickListener {
            viewModel.saveButtonPressed(responseData, requireContext())
        }

        viewModel.currentPage.observe(viewLifecycleOwner) {
            it?.let {
                isCurrentPage = it.second == pageId
                if (isCurrentPage) refreshPage()
            }
        }

        viewModel.data.observe(viewLifecycleOwner) {
            if (isCurrentPage) {
                when (it) {
                    is RequestState.ERROR -> {
                        picLoading(true)
                        Toast.makeText(
                            requireContext(),
                            "There's an error loading the image",
                            Toast.LENGTH_LONG
                        )
                    }
                    is RequestState.PENDING -> {
                        picLoading(false)
                    }
                    is RequestState.SUCCESS ->
                    {
                        picLoading(true)
                        requestingUrl = it.response.image_url
                        responseData = it.response
                        hasPicLoading(false)
                        viewModel.hasImage(it.response.image_url)
                    }
                }
            }
        }

        // This is what loads the image whenever requested.
        // It becomes async because if the random URL received was actually
        //      displaying the same image that the user previously saved,
        //      the Save button needs to change to an Unsave button.
        viewModel.hasImageResult.observe(viewLifecycleOwner) { result ->
            // Due to race condition, requestUrl needs a check.
            if (isCurrentPage && requestingUrl.isNotEmpty()) {
                result?.let {
                    Glide.with(this)
                        .load(requestingUrl)
                        .into(binding.cutePicImage)
                    setSaveButtonMode(it)
                }
            }
        }

        viewModel.savingImage.observe(viewLifecycleOwner) {
            hasPicLoading(!it)
        }
        viewModel.unsavingImage.observe(viewLifecycleOwner) {
            hasPicLoading(!it)
        }

        return binding.root
    }

    protected abstract fun refreshPage()

    private fun setSaveButtonMode(is_unsave: Boolean) {
        if (is_unsave) binding.cutePicSave.text = "Unsave"
        else binding.cutePicSave.text = "Save"
        hasPicLoading(true)
    }

    private fun picLoading(done_loading: Boolean) {
        binding.cutePicRefresh.isEnabled = done_loading
        binding.cutePicSave.isEnabled = done_loading
        binding.picLoading.visibility = if (done_loading) View.INVISIBLE else View.VISIBLE
    }

    private fun hasPicLoading(done_loading: Boolean) {
        binding.cutePicSave.isEnabled = done_loading
        binding.hasPicLoading.visibility = if (done_loading) View.INVISIBLE else View.VISIBLE
    }
}
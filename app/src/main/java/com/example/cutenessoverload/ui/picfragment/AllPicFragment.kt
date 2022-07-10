package com.example.cutenessoverload.ui.picfragment

class AllPicFragment : BasePicFragment() {
    override var pageId: Int = 0

    override fun refreshPage() {
        viewModel.getRandom()
    }
}
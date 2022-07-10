package com.example.cutenessoverload.ui.picfragment

class FoxPicFragment : BasePicFragment() {
    override var pageId: Int = 4

    override fun refreshPage() {
        viewModel.getRandomFox()
    }
}
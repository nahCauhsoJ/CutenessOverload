package com.example.cutenessoverload.ui.picfragment

class DuckPicFragment : BasePicFragment() {
    override var pageId: Int = 3

    override fun refreshPage() {
        viewModel.getRandomDuck()
    }
}
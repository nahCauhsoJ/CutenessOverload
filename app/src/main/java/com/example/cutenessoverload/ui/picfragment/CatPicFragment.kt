package com.example.cutenessoverload.ui.picfragment

class CatPicFragment : BasePicFragment() {
    override var pageId = 1

    override fun refreshPage() {
        viewModel.getRandomCat()
    }
}
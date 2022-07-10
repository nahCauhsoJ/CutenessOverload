package com.example.cutenessoverload.ui.picfragment

class DogPicFragment : BasePicFragment() {
    override var pageId: Int = 2

    override fun refreshPage() {
        viewModel.getRandomDog()
    }
}
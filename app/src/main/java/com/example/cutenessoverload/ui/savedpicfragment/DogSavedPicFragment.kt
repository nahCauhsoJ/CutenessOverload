package com.example.cutenessoverload.ui.savedpicfragment

class DogSavedPicFragment: BaseSavedPicFragment() {
    override var pageId: Int = 2
    override var requesterId: String = "dog"
    override fun getLocalData() {
        viewModel.getLocalData(requesterId, "dog")
    }
}
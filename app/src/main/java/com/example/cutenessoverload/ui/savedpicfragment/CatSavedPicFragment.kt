package com.example.cutenessoverload.ui.savedpicfragment

class CatSavedPicFragment: BaseSavedPicFragment() {
    override var pageId: Int = 1
    override var requesterId: String = "cat"
    override fun getLocalData() {
        viewModel.getLocalData(requesterId,"cat")
    }
}
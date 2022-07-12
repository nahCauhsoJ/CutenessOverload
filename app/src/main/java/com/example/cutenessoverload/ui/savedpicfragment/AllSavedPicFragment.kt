package com.example.cutenessoverload.ui.savedpicfragment

class AllSavedPicFragment: BaseSavedPicFragment() {
    override var pageId: Int = 0
    override var requesterId: String = "all"
    override fun getLocalData() {
        viewModel.getLocalData(requesterId)
    }
}
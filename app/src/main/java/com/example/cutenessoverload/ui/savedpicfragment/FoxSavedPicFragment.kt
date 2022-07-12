package com.example.cutenessoverload.ui.savedpicfragment

class FoxSavedPicFragment: BaseSavedPicFragment() {
    override var pageId: Int = 4
    override var requesterId: String = "fox"
    override fun getLocalData() {
        viewModel.getLocalData(requesterId, "fox")
    }
}
package com.example.cutenessoverload.ui.savedpicfragment

class DuckSavedPicFragment: BaseSavedPicFragment() {
    override var pageId: Int = 3
    override var requesterId: String = "duck"
    override fun getLocalData() {
        viewModel.getLocalData(requesterId, "duck")
    }
}
package com.example.cutenessoverload.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cutenessoverload.databinding.SavedPicCardBinding
import com.example.cutenessoverload.model.CuteGeneric

class SavedPicAdapter(
    private val savedPicList: MutableList<CuteGeneric> = mutableListOf(),
    private val savedPicViewHolder: MutableSet<ViewHolder> = mutableSetOf(),
    private val informClickedPic: (CuteGeneric) -> Boolean?
) : RecyclerView.Adapter<SavedPicAdapter.ViewHolder>() {

    fun refreshList(cuteGenericList: List<CuteGeneric>) {
        savedPicList.clear()
        savedPicList.addAll(cuteGenericList)
        notifyDataSetChanged()
    }

    fun clearUnsaveIndicator() {
        for (i in savedPicViewHolder) i.clearUnsaveIndicator()
    }

    fun deleteItems(cuteGenericList: List<CuteGeneric>) {
        savedPicList.removeAll(cuteGenericList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: SavedPicCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cuteGeneric: CuteGeneric) {
            Glide.with(binding.root)
                .load(cuteGeneric.image_url)
                .into(binding.savedPicView)
            binding.unsavePicIndicator.visibility = View.INVISIBLE
            binding.savedPicView.setOnClickListener {
                informClickedPic(cuteGeneric)?.let {
                    binding.unsavePicIndicator.visibility =
                        if (it) View.VISIBLE else View.INVISIBLE
                }
            }
        }
        fun clearUnsaveIndicator() {
            binding.unsavePicIndicator.visibility = View.INVISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            SavedPicCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(savedPicList[position])
        // Needed solely for clearing the broken hearts
        savedPicViewHolder.add(holder)
    }

    override fun getItemCount(): Int = savedPicList.size
}
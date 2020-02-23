package com.aiden.memo.presentation.feature.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aiden.memo.databinding.ItemImageBinding

class ImageListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val imageList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageViewHolder).bind(imageList[position])
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String) {
            binding.run {
                this.image = image
            }
        }
    }

    fun setList(imageUriList: List<String>?, linkList: List<String>?) {
        imageList.clear()
        imageUriList?.let {
            imageList.addAll(it)
            linkList?.let { linkList ->
                imageList.addAll(linkList)
            }
        }
        notifyDataSetChanged()
    }

}
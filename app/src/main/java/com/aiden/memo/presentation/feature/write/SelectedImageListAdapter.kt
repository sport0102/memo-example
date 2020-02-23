package com.aiden.memo.presentation.feature.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aiden.memo.databinding.ItemSeletedImageBinding

class SelectedImageListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val imageList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SelectedImageViewHolder(
            ItemSeletedImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SelectedImageViewHolder).bind(imageList[position])
    }

    inner class SelectedImageViewHolder(private val binding: ItemSeletedImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String) {
            binding.run {
                this.image = image
            }
        }
    }

    fun setList(list: List<String>?) {
        imageList.clear()
        list?.let {
            imageList.addAll(list)
        }
        notifyDataSetChanged()
    }

}
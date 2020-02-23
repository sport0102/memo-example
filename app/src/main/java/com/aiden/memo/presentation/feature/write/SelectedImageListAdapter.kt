package com.aiden.memo.presentation.feature.write

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aiden.memo.databinding.ItemSeletedImageBinding

class SelectedImageListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val imageList = arrayListOf<Uri>()

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
        fun bind(image: Uri) {
            binding.run {
                this.image = image.toString()
            }
        }
    }

    fun setList(list: List<Uri>) {
        imageList.clear()
        imageList.addAll(list)
        notifyDataSetChanged()
    }

}
package com.aiden.memo.presentation.feature.write

import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aiden.memo.databinding.ItemLinkBinding
import java.util.regex.Pattern

class LinkListAdapter(val viewModel: WriteViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val linkList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LinkViewHolder(
            ItemLinkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = linkList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LinkViewHolder).bind(linkList[position], position)
    }

    inner class LinkViewHolder(private val binding: ItemLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(link: String, position: Int) {
            binding.run {
                this.link = link
                itemLinkBtnDelete.setOnClickListener {
                    viewModel.deleteLink(position)
                }
            }
        }
    }

    fun setList(list: List<String>?) {
        linkList.clear()
        list?.let {
            linkList.addAll(list)
        }
        notifyDataSetChanged()
    }

}
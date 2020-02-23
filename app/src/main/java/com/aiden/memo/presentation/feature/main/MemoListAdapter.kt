package com.aiden.memo.presentation.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aiden.memo.databinding.ItemMemoBinding
import com.aiden.memo.presentation.model.MemoModel

class MemoListAdapter(val viewModel : MainViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val memoInfoList = arrayListOf<MemoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MemoInfoViewHolder(
            ItemMemoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = memoInfoList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MemoInfoViewHolder).bind(memoInfoList[position])
    }

    inner class MemoInfoViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(memoInfoModel: MemoModel) {
            binding.run {
                item = memoInfoModel
                itemMemoCl.setOnClickListener {
                    viewModel.setSeletedItem(memoInfoModel.id)
                }
            }
        }
    }

    fun setMemoList(list: List<MemoModel>) {
        memoInfoList.clear()
        memoInfoList.addAll(list)
        notifyDataSetChanged()
    }

}
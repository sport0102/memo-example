package com.aiden.memo.presentation.feature.detail

import android.os.Bundle
import com.aiden.memo.R
import com.aiden.memo.databinding.ActivityDetailBinding
import com.aiden.memo.presentation.base.BaseActivity
import com.aiden.memo.presentation.event.EventObserver
import com.aiden.memo.presentation.feature.write.SelectedImageListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity :
    BaseActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {
    override val viewModel by viewModel<DetailViewModel>()
    private val intentKeyId = "id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent?.getStringExtra(intentKeyId)
        setLayout()
        setViewModel(id)
    }

    private fun setLayout() {
        binding {
            detailRvSeletedImage.adapter = SelectedImageListAdapter()

            detailBtnDelete.setOnClickListener {
                viewModel.deleteMemo()
            }

        }
    }

    private fun setViewModel(id: String?) {
        id?.let { viewModel.getMemoById(it) }
        viewModel.isDataLoadingError.observe(this, EventObserver {
            if (it.second) {
                toastM("${it.first}")
            }
        })
        viewModel.memo.observe(this, EventObserver {
            binding.memo = it
            (binding.detailRvSeletedImage.adapter as SelectedImageListAdapter).setList(it.imageList)
        })
        viewModel.isDeleted.observe(this, EventObserver {
            val message =
                if (it) getString(R.string.detail_delete_success) else getString(R.string.detail_delete_fail)
            toastM(message)
            finish()
        })
    }
}
package com.aiden.memo.presentation.feature.main

import android.content.Intent
import android.os.Bundle
import com.aiden.memo.R
import com.aiden.memo.databinding.ActivityMainBinding
import com.aiden.memo.presentation.base.BaseActivity
import com.aiden.memo.presentation.event.EventObserver
import com.aiden.memo.presentation.feature.write.WriteActivity
import com.aiden.memo.presentation.feature.write.WriteType
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModel by viewModel<MainViewModel>()
    private val intentKeyWriteType = "writeType"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayout()
    }

    private fun setLayout() {
        binding {
            mainRv.adapter = MemoListAdapter()
            mainFb.setOnClickListener {
                val intent = Intent(this@MainActivity, WriteActivity::class.java)
                intent.putExtra(intentKeyWriteType, WriteType.CREATE)
                startActivity(intent)
            }
        }
        viewModel.memoList.observe(this, EventObserver {
            if (it.isNullOrEmpty()) {
                viewModel.setHasMemo(false)
            } else {
                viewModel.setHasMemo(true)
                (binding.mainRv.adapter as MemoListAdapter).setMemoList(it)
            }
            viewModel.setDataLoadingStatus(false)
        })
        viewModel.isDataLoadingError.observe(this, EventObserver {
            if (it.second) {
                toastM("${it.first}")
            }
        })
    }
}
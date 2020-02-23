package com.aiden.memo.presentation.feature.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.aiden.memo.R
import com.aiden.memo.databinding.ActivityMainBinding
import com.aiden.memo.presentation.base.BaseActivity
import com.aiden.memo.presentation.event.EventObserver

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

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
            (binding.mainRv.adapter as MemoListAdapter).setMemoList(it)
        })
        viewModel.isDataLoadingError.observe(this, EventObserver {
            if (it.second) {
                toastM("${it.first}")
            }
        })
    }
}
package com.aiden.memo.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.aiden.memo.BR

abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {

    protected lateinit var binding: B

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutResId, null, false)
        binding {
            binding.lifecycleOwner = this@BaseActivity
            binding.setVariable(BR.vm, viewModel)
        }
        setContentView(binding.root)
    }

    protected fun binding(action: B.() -> Unit) {
        binding.run(action)
    }

    protected fun toastM(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
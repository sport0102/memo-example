package com.aiden.memo.presentation.ext

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:visibleGoneIf")
fun View.visibleGoneIf(visibleGoneIf: Boolean?) {
    visibleGoneIf?.let {
        this.visibility = if (it) View.GONE else View.VISIBLE
    }
}
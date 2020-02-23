package com.aiden.memo.presentation.ext

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.aiden.memo.R
import com.bumptech.glide.Glide

@BindingAdapter("bind:imageSrc")
fun ImageView.bindImageSrc(imageSrc: String?) {
    imageSrc?.let {
        Glide.with(this.context)
            .load(it)
            .placeholder(R.drawable.image_place_holder)
            .fitCenter()
            .into(this)
    }
}

@BindingAdapter("bind:imageUri")
fun ImageView.bindImageUri(imageUri: Uri?) {
    imageUri?.let {
        Glide.with(this.context)
            .load(it)
            .placeholder(R.drawable.image_place_holder)
            .centerCrop()
            .into(this)
    }
}
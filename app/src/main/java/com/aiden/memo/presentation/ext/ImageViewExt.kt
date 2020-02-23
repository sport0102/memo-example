package com.aiden.memo.presentation.ext

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.aiden.memo.R
import com.bumptech.glide.Glide

@BindingAdapter("bind:image")
fun ImageView.bindImage(image: String?) {
    image?.let { path ->
        when {
            path.startsWith("http") -> {
                loadImage(this, path)
            }
            path.startsWith("file") -> {
                loadImage(this, Uri.parse(path))
            }
            else -> {
                loadImage(this)
            }
        }
    } ?: loadImage(this)

}

private fun loadImage(view: ImageView, path: String) {
    Glide.with(view.context)
        .load(path)
        .placeholder(R.drawable.image_place_holder)
        .centerCrop()
        .into(view)
}

private fun loadImage(view: ImageView) {
    Glide.with(view.context)
        .load(R.drawable.image_place_holder)
        .placeholder(R.drawable.image_place_holder)
        .centerCrop()
        .into(view)
}

private fun loadImage(view: ImageView, path: Uri) {
    Glide.with(view.context)
        .load(path)
        .placeholder(R.drawable.image_place_holder)
        .centerCrop()
        .into(view)
}
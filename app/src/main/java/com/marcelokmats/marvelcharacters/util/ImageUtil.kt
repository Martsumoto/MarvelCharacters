package com.marcelokmats.marvelcharacters.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.marcelokmats.marvelcharacters.R
import com.marcelokmats.marvelcharacters.model.Thumbnail

enum class IMAGE_PORTRAIT_SIZES {
    portrait_small,
    portrait_medium,
    portrait_xlarge,
    portrait_fantastic,
    portrait_uncanny,
    portrait_incredible
}

fun ImageView.setUrl(context: Context, thumbnail: Thumbnail) {
    Glide.with(context)
        .load(imageUrlBuilder(thumbnail))
        .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
        .into(this)
}

private fun imageUrlBuilder(thumbnail: Thumbnail, imageSize : String = IMAGE_PORTRAIT_SIZES.portrait_medium.name) =
    thumbnail.path + "/" + imageSize + "." + thumbnail.extension
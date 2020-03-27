package com.dfodor.motionlayout.sample

import androidx.annotation.DrawableRes

data class MovieItem(
    val title: String,
    val tags: String,
    val description: String,
    @DrawableRes val drawableId: Int
)
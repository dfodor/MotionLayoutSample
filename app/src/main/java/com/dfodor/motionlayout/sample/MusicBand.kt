package com.dfodor.motionlayout.sample

import androidx.annotation.DrawableRes

data class MusicBand(
    val name: String,
    val tags: String,
    val description: String,
    @DrawableRes val drawableId: Int
)
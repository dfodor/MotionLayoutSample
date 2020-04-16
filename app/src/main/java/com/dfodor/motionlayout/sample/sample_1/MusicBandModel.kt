package com.dfodor.motionlayout.sample.sample_1

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MusicBandModel(
    @StringRes val name: Int,
    @StringRes val tags: Int,
    @StringRes val shortDescriptionStringRes: Int,
    @StringRes val aboutStringRes: Int,
    @DrawableRes val drawableId: Int
)
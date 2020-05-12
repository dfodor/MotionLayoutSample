package com.dfodor.motionlayout.sample

object TestDataHelper {

    fun getMusicBandItems() = listOf(
        MusicBandModel(
            name = R.string.pink_floyd,
            tags = R.string.pink_floyd_tags,
            shortDescriptionStringRes = R.string.pink_floyd_short_description,
            aboutStringRes = R.string.pink_floyd_about,
            drawableId = R.drawable.pink_floyd_dark_side_of_the_moon
        ),
        MusicBandModel(
            name = R.string.boc,
            tags = R.string.boc_tags,
            shortDescriptionStringRes = R.string.boc_short_description,
            aboutStringRes = R.string.boc_about,
            drawableId = R.drawable.blue_oyster_cult_secret_treaties
        ),
        MusicBandModel(
            name = R.string.camel,
            tags = R.string.camel_tags,
            shortDescriptionStringRes = R.string.camel_short_description,
            aboutStringRes = R.string.camel_about,
            drawableId = R.drawable.camel_mirage
        ),
        MusicBandModel(
            name = R.string.king_crimson,
            tags = R.string.king_crimson_tags,
            shortDescriptionStringRes = R.string.king_crimson_short_description,
            aboutStringRes = R.string.king_crimson_about,
            drawableId = R.drawable.king_crimson_in_the_court_of_the_crimson_king
        )
    )
}
package com.dfodor.motionlayout.sample

fun getTestItems() = listOf(
    MusicBandModel(
        name = "Pink Floyd",
        tags = "Psychedelic/Garage, Progressive Rock, Art Rock, Hard Rock",
        shortDescriptionStringRes = R.string.pink_floyd_short_description,
        aboutStringRes = R.string.pink_floyd_about,
        drawableId = R.drawable.pink_floyd_dark_side_of_the_moon
    ),
    MusicBandModel(
        name = "Blue Öyster Cult",
        tags = "Hard Rock, Heavy Metal, Album Rock",
        shortDescriptionStringRes = R.string.boc_short_description,
        aboutStringRes = R.string.boc_about,
        drawableId = R.drawable.blue_oyster_cult_secret_treaties
    ),
    MusicBandModel(
        name = "Camel",
        tags = "Progressive Rock, Art Rock",
        shortDescriptionStringRes = R.string.camel_short_description,
        aboutStringRes = R.string.camel_about,
        drawableId = R.drawable.camel_mirage
    ),
    MusicBandModel(
        name = "King Crimson",
        tags = "Progressive Rock, Art Rock, Album Rock",
        shortDescriptionStringRes = R.string.king_crimson_short_description,
        aboutStringRes = R.string.king_crimson_about,
        drawableId = R.drawable.king_crimson_in_the_court_of_the_crimson_king
    )
)
package com.dfodor.motionlayout.sample

private const val LOREM =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor" +
            " incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud" +
            " exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
            "irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla" +
            " pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui " +
            "officia deserunt mollit anim id est laborum."

fun getTestItems() = listOf(
    MusicBand(
        name = "Pink Floyd",
        tags = "Psychedelic/Garage, Progressive Rock, Art Rock, Hard Rock",
        description = LOREM.takeLast(260).trim(),
        drawableId = R.drawable.pink_floyd_dark_side_of_the_moon
    ),
    MusicBand(
        name = "Blue Öyster Cult",
        tags = "Hard Rock, Heavy Metal, Album Rock",
        description = LOREM.take(220).trim(),
        drawableId = R.drawable.blue_oyster_cult_secret_treaties
    ),
    MusicBand(
        name = "Camel",
        tags = "Progressive Rock, Art Rock",
        description = LOREM.take(300).trim(),
        drawableId = R.drawable.camel_mirage
    ),
    MusicBand(
        name = "King Crimson",
        tags = "Progressive Rock, Art Rock, Album Rock",
        description = LOREM.takeLast(190).trim(),
        drawableId = R.drawable.king_crimson_in_the_court_of_the_crimson_king
    )
)
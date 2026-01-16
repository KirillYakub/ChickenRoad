package com.chicken.chickenroad.util

object Constants {

    const val PERMISSION_REQUEST_CODE = 101

    const val BUTTON_WIDTH_ASPECT_RATIO = 1.5

    const val PADDING_CALCULATION_DIVISOR_LARGE = 10
    const val PADDING_CALCULATION_DIVISOR_MEDIUM = 6
    const val PADDING_CALCULATION_DIVISOR_SMALL = 4
    const val PADDING_CALCULATION_DIVISOR_TINY = 2

    const val APP_DATASTORE_PREFERENCES = "APP_DATASTORE_PREFERENCES"
    const val MUSIC_DATA_KEY = "MUSIC_DATA_KEY"
    const val SOUND_DATA_KEY = "SOUND_DATA_KEY"
    const val FIRST_INPUT_DATA_KEY = "FIRST_INPUT_KEY"
    const val MAX_LEVEL_DATA_KEY = "MAX_LEVEL_KEY"
    const val RATING_DATA_KEY = "RATING_DATA_KEY"

    const val LEADERBOARD_TABLE_NAME = "leaderboard_table"
    const val DATABASE_NAME = "leaders_database"

    const val MAX_LEVEL = 5
    const val LEVEL_DEFAULT_TIME = 40
    const val DECREASE_PER_LEVEL = 5

    const val GAME_LEVEL_ARG = "ARG"

    const val PRIVACY_URL = "https://www.freeprivacypolicy.com/live/765c09db-807b-461f-89b2-a3dc901e7217"
    const val TERMS_URL = "https://www.freeprivacypolicy.com/live/0616a494-f68f-4247-9c4f-dc12d14dbe84"
}

val leadersList = listOf(
    Pair("Lunara", 18L),
    Pair("Vexon", 24L),
    Pair("Elira", 28L),
    Pair("Korix", 32L),
    Pair("Solen", 36L)
)
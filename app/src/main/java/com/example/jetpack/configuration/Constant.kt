package com.example.jetpack.configuration

object Constant {
    const val SETTING_DATASTORE = "setting_datastore"
    const val TUTORIAL_DATASTORE = "tutorial_datastore"

    const val NOTIFICATION_ID = 999
    const val LOCKSCREEN_ID = 888

    const val LOCKSCREEN_CHANNEL_ID = "Wehrmacht"
    const val NOTIFICATION_CHANNEL_ID = "Bundeswehr"

    /* notification milestone 1 at 10h39 */
    const val NOTIFICATION_MILESTONE_1_HOUR = 10
    const val NOTIFICATION_MILESTONE_1_MINUTE = 39

    /* notification milestone 2 at 16h04 */
    const val NOTIFICATION_MILESTONE_2_HOUR = 16
    const val NOTIFICATION_MILESTONE_2_MINUTE = 4

    /* notification milestone 3 at 22h39 */
    const val NOTIFICATION_MILESTONE_3_HOUR = 22
    const val NOTIFICATION_MILESTONE_3_MINUTE = 39

    /* AccuWeather - https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/autocomplete */
    const val ACCU_WEATHER_KEY = "x5ADcexs7Ge2ge8ZlB8T8JnJtWG4AhSJ"
    const val ACCU_WEATHER_URL = "https://dataservice.accuweather.com"

    /* default range */
    val defaultRange:  Pair<Float, Float> = 0F to 300F

    /* FOREGROUND SERVICE */
    const val FOREGROUND_SERVICE_CHANNEL_ID = "foreground_service_id"
    const val FOREGROUND_SERVICE_NOTIFICATION_ID = 1
    const val FOREGROUND_SERVICE_CHANNEL_NAME = "foreground_service"
    const val FOREGROUND_SERVICE_CHANNEL_DESCRIPTION = "foreground_service_description"
    const val FOREGROUND_SERVICE_ACTION_START = "foreground_service_action_start"
    const val FOREGROUND_SERVICE_ACTION_STOP = "foreground_service_action_stop"

    const val DEFAULT_SCREEN_RECORDING_TIME = 9999
    const val SECOND_ON_MINUTE = 60 // a minute has 60 seconds
    const val MINUTE_ON_HOUR = 60 // a hour has 60 minutes

    const val INITIAL_PAGE_HOUR = 4800 // we have to initial page to make time picker loop when it reach 23h
    const val INITIAL_PAGE_MINUTE = 6000 // we have to initial page to make time picker loop when it reach 23h
    const val INITIAL_PAGE_SECOND = 6000 // we have to initial page to make time picker loop when it reach 23h
    const val MAXIMUM_PAGE = 10000 // we have to initial page to make time picker loop when it reach 23h
}
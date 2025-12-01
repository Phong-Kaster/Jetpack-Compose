package com.example.jetpack.domain.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jetpack.R

enum class NotificationMessage(
    @DrawableRes val photo: Int,
    @StringRes val title: Int,
    @StringRes val subtitle: Int,
    @StringRes val textButton: Int,
) {
    FIRST_START(
        photo = R.drawable.ic_nazi_swastika,
        title = R.string.notification_after_5_minute,
        subtitle = R.string.fake_message,
        textButton = R.string.ok,
    ),
    FREE_CREDITS(
        photo = R.drawable.ic_language_german,
        title = R.string.fake_title,
        subtitle = R.string.fake_message,
        textButton = R.string.not_now,
    ),
    AI_MATCH(
        photo = R.drawable.ic_nazi_wehtmatch,
        title = R.string.fake_title,
        subtitle = R.string.fake_message,
        textButton = R.string.let_s_go,
    ),
    VIRAL_EFFECT(
        photo = R.drawable.ic_heer,
        title = R.string.fake_title,
        subtitle = R.string.fake_message,
        textButton = R.string.let_s_go,
    ),
    ;
    companion object {
        fun getByName(name: String?): NotificationMessage {
            return if(name == null) FIRST_START else entries.find { it.name == name } ?: FIRST_START
        }
    }
}
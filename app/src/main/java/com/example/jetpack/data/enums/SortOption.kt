package com.example.jetpack.data.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.example.jetpack.R

@Immutable
enum class SortOption(
    @StringRes val text: Int,
    @DrawableRes val leadingIcon: Int
) {
    Original(text = R.string.original, leadingIcon = R.drawable.ic_sort_original),
    AlphabetAscending(text = R.string.alphabet_ascending, leadingIcon = R.drawable.ic_sort_alphabet_ascending),
    AlphabetDescending(text = R.string.alphabet_descending, leadingIcon = R.drawable.ic_sort_alphabet_descending),
    Inverted(text = R.string.alphabet_descending, leadingIcon = R.drawable.ic_sort_inverted),
}
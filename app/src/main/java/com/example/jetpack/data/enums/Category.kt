package com.example.jetpack.data.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jetpack.R


enum class Category
constructor(
    @DrawableRes val icon: Int,
    @StringRes val text: Int,
) {
    InvestmentAndSaving(icon = R.drawable.ic_toturial, text = R.string.investment_saving),
    FixedExpenses(icon = R.drawable.ic_language, text = R.string.fixed_expenses),
    Business(icon = R.drawable.ic_private_policy, text = R.string.business),
    UnexpectedExpenses(icon = R.drawable.ic_disclaimer, text = R.string.unexpected_expenses),
    LivingExpenses(icon = R.drawable.ic_term_of_service, text = R.string.living_expenses),

    Event(icon = R.drawable.ic_language_english, text = R.string.events),
    Shopping(icon = R.drawable.ic_language_german, text = R.string.shopping),
    Entertainment(icon = R.drawable.ic_language_japanese, text = R.string.entertainment),
    Charity(icon = R.drawable.ic_language_vietnamese, text = R.string.charity),
    Education(icon = R.drawable.ic_language_korean, text = R.string.education),
    LoanPayment(icon = R.drawable.ic_language_hindi, text = R.string.loan_payment),
    ;

    companion object {
        fun Category.getSubcategories(): List<Category> {
            return when (this) {
                InvestmentAndSaving -> listOf(Education)
                FixedExpenses -> listOf()
                Business -> listOf()
                UnexpectedExpenses -> listOf(Event, Shopping, LoanPayment)
                LivingExpenses -> listOf(Entertainment, Charity)
                else -> listOf()
            }
        }
    }
}
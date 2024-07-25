package com.example.jetpack.domain.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jetpack.R


enum class Category
constructor(
    @DrawableRes val icon: Int,
    @StringRes val text: Int,
    val isParent: Boolean
) {
    InvestmentAndSaving(icon = R.drawable.ic_toturial, text = R.string.investment_saving, isParent = true),
    FixedExpenses(icon = R.drawable.ic_language, text = R.string.fixed_expenses, isParent = true),
    Business(icon = R.drawable.ic_private_policy, text = R.string.business, isParent = true),
    UnexpectedExpenses(icon = R.drawable.ic_disclaimer, text = R.string.unexpected_expenses, isParent = true),
    LivingExpenses(icon = R.drawable.ic_term_of_service, text = R.string.living_expenses, isParent = true),

    Event(icon = R.drawable.ic_launcher_foreground, text = R.string.events, isParent = false),
    Shopping(icon = R.drawable.ic_launcher_foreground, text = R.string.shopping, isParent = false),
    Entertainment(icon = R.drawable.ic_launcher_foreground, text = R.string.entertainment, isParent = false),
    Charity(icon = R.drawable.ic_launcher_foreground, text = R.string.charity, isParent = false),
    Education(icon = R.drawable.ic_launcher_foreground, text = R.string.education, isParent = false),
    LoanPayment(icon = R.drawable.ic_launcher_foreground, text = R.string.loan_payment, isParent = false),
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
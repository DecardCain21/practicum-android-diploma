package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.R

object EndingConvertor {

    @Suppress("MagicNumber")
    fun vacancies(amount: Int): Int = when {
        amount % 100 in 11..19 -> R.string.vacancies_genitive
        amount % 10 == 1 -> R.string.vacancy_nominative
        amount % 10 in 2..4 -> R.string.vacancy_dative
        else -> R.string.vacancies_genitive
    }

}

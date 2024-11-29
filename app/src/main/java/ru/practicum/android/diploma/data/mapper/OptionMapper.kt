package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.domain.models.Filter

class OptionMapper {

    fun map(
        expression: String,
        page: Int,
        filter: Filter
    ): Map<String, Any> {
        val options = mutableMapOf<String, Any>()
        options["text"] = expression
        options["page"] = page
        options["per_page"] = COUNT_PER_PAGE
        options["only_with_salary"] = filter.withoutSalaryButton

        filter.country?.let { options["area"] = it.id }
        filter.salary?.let { options["salary"] = it }
        filter.industry?.let { options["industry"] = it }

        return options
    }

    companion object {
        const val COUNT_PER_PAGE = "20"
    }
}

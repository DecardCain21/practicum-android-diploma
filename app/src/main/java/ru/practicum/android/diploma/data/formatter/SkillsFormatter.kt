package ru.practicum.android.diploma.data.formatter

import ru.practicum.android.diploma.data.dto.vacancy.nested.KeySkillDto
import kotlin.text.Typography.middleDot

object SkillsFormatter {

    fun format(list: List<KeySkillDto>): String =
        list.mapIndexed { _, keySkillDto -> "$middleDot  ${keySkillDto.name}" }
            .joinToString("\n")
}

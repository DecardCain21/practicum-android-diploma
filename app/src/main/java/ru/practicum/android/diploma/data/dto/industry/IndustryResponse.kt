package ru.practicum.android.diploma.data.dto.industry

import ru.practicum.android.diploma.data.dto.Response

data class IndustryResponse(
    val industries: List<IndustryItemDto>
) : Response()

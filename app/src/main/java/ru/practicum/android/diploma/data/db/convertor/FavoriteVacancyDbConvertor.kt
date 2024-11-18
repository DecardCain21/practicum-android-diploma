package ru.practicum.android.diploma.data.db.convertor

import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavoriteVacancyDbConvertor {

    fun map(model: VacancyDetails) = FavoriteVacancyEntity(
        id = model.id,
        name = model.name,
        city = model.city,
        imageUrl = model.imageUrl,
        salaryFrom = model.salaryFrom,
        salaryTo = model.salaryTo,
        currency = model.currency,
        employmentName = model.employerName,
        experience = model.experience,
        schedule = model.schedule,
        employerName = model.employmentName,
        description = model.description,
        descriptionSkills = model.descriptionSkills,
        timestamp = System.currentTimeMillis(),
        url = model.url
    )

    fun map(entity: FavoriteVacancyEntity) = VacancyDetails(
        id = entity.id,
        name = entity.name,
        city = entity.city,
        imageUrl = entity.imageUrl,
        salaryFrom = entity.salaryFrom,
        salaryTo = entity.salaryTo,
        currency = entity.currency,
        employmentName = entity.employmentName,
        experience = entity.experience,
        employerName = entity.employerName,
        schedule = entity.schedule,
        description = entity.description,
        descriptionSkills = entity.descriptionSkills,
        url = entity.url
    )
}

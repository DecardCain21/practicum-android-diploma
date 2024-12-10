package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.db.convertor.FavoriteVacancyDbConvertor
import ru.practicum.android.diploma.data.db.dao.FavoriteVacanciesDao
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavoriteVacanciesRepositoryImpl(
    private val favoriteDao: FavoriteVacanciesDao,
    private val convertor: FavoriteVacancyDbConvertor
) : FavoriteVacanciesRepository {

    override suspend fun add(vacancy: VacancyDetails) =
        favoriteDao.add(convertor.map(vacancy))

    override suspend fun getById(id: String): VacancyDetails? {
        val entity = favoriteDao.getById(id) ?: return null
        return convertor.mapToDetails(entity)
    }

    override suspend fun getAll(): List<Vacancy> =
        favoriteDao.getAll().map { convertor.map(it) }

    override suspend fun delete(vacancy: VacancyDetails) =
        favoriteDao.delete(convertor.map(vacancy))
}

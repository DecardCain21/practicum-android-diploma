package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacanciesDao {

    @Insert
    suspend fun add(vacancy: FavoriteVacancyEntity)

    @Query("SELECT * FROM favorite_vacancies WHERE id = :id")
    suspend fun getById(id: String): FavoriteVacancyEntity?

    @Query("SELECT * FROM favorite_vacancies ORDER BY timestamp DESC")
    suspend fun getAll(): List<FavoriteVacancyEntity>

    @Delete
    suspend fun delete(vacancy: FavoriteVacancyEntity)

}

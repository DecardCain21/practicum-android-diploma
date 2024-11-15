package ru.practicum.android.diploma

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.domain.usecase.GetVacanciesUseCase

@ExperimentalCoroutinesApi
class SearchRequestTest {
    private val testUseCase: GetVacanciesUseCase = mockk()

    @Test
    fun testGetVacancies() = runTest {
        // Настройте ваши ожидания
        val expectedResult = mockk<Pair<VacancySearchResult?, String?>>()
        coEvery { testUseCase.execute("Android разработчик", 1) } returns expectedResult

        // Запустите ваш код в корутине
        val resultDeferred = async(Dispatchers.IO) {
            testUseCase.execute("Android разработчик", 1)
        }

        // Получите результат
        val result = resultDeferred.await()

        // Проверьте результат
        // assertEquals(expectedResult, result)
        println(result)
        //  tag:system.out
    }
}

package com.degalex.waadsutest.interactors

import com.degalex.waadsutest.data.repositories.interfaces.CoordinatesRepository
import com.degalex.waadsutest.domain.exceptions.CoordinatesInvalidExceptions
import com.degalex.waadsutest.domain.interactors.implementation.GetTerritoryInteractorDefault
import com.degalex.waadsutest.domain.mappers.TerritoryMapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetTerritoryInteractorTest {

    private val mockCoordinatesRepository = mock<CoordinatesRepository>()

    private val territoryMapper = TerritoryMapper()
    private val getTerritoryInteractor = GetTerritoryInteractorDefault(
        coordinatesRepository = mockCoordinatesRepository,
        territoryMapper = territoryMapper,
    )

    @Test
    fun getTerritoryInteractor_coordinatesCorrect_returnTerritory() {
        runBlocking {
            val coordinates = getCorrectMockCoordinates()

            whenever(mockCoordinatesRepository.getCoordinates()).thenReturn(coordinates)

            assertEquals(getTerritoryInteractor(), territoryMapper.toDomain(coordinates))
        }
    }

    @Test
    fun getTerritoryInteractor_coordinatesIncorrect_throwException() {
        runBlocking {
            val coordinates = getIncorrectMockCoordinates()

            whenever(mockCoordinatesRepository.getCoordinates()).thenReturn(coordinates)

            assertThrows(CoordinatesInvalidExceptions::class.java) {
                runBlocking { getTerritoryInteractor() }
            }
        }
    }

    private fun getCorrectMockCoordinates(): List<List<List<List<Double>>>> {
        return listOf(
            listOf(listOf(listOf(0.0, 0.0), listOf(1.0, 2.0), listOf(3.0, 4.0), listOf(4.0, 8.0))),
            listOf(listOf(listOf(10.0, 0.0), listOf(23.0, 10.0), listOf(34.0, 6.0))),
            listOf(listOf(listOf(0.0, 0.0), listOf(1.0, 2.0), listOf(3.0, 4.0), listOf(4.0, 8.0))),
        )
    }

    private fun getIncorrectMockCoordinates(): List<List<List<List<Double>>>> {
        return listOf(
            listOf(listOf(listOf(1000.0, -600.0), listOf(2.0), emptyList(), listOf(4.0, 8.0, 1.0))),
            listOf(listOf(listOf(10.0, 0.0))),
            emptyList(),
            listOf(emptyList(), emptyList()),
            listOf(listOf(emptyList())),
        )
    }
}
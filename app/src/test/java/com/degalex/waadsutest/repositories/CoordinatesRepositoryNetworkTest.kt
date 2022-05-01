package com.degalex.waadsutest.repositories

import com.degalex.waadsutest.data.datasources.network.WaadsuApiCalls
import com.degalex.waadsutest.data.entities.network.FeatureNetwork
import com.degalex.waadsutest.data.entities.network.GeoPointsNetwork
import com.degalex.waadsutest.data.entities.network.GeometryNetwork
import com.degalex.waadsutest.data.repositories.network.CoordinatesRepositoryNetwork
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CoordinatesRepositoryNetworkTest {

    private val mockWaadsuApiCalls = mock<WaadsuApiCalls>()

    private val coordinatesRepository = CoordinatesRepositoryNetwork(mockWaadsuApiCalls)

    @Test
    fun coordinatesRepositoryNetwork_getCoordinates_coordinatesNotEmpty() {
        runBlocking {
            val coordinates = getNotEmptyCoordinates()

            whenever(mockWaadsuApiCalls.getGeoPoints()).thenReturn(convertToGeoPoints(coordinates))

            assertEquals(coordinatesRepository.getCoordinates(), coordinates)
        }
    }

    @Test
    fun coordinatesRepositoryNetwork_getCoordinates_coordinatesEmpty() {
        runBlocking {
            val coordinates = getEmptyCoordinates()

            whenever(mockWaadsuApiCalls.getGeoPoints()).thenReturn(convertToGeoPoints(coordinates))

            assertEquals(coordinatesRepository.getCoordinates(), coordinates)
        }
    }

    private fun convertToGeoPoints(coordinates: List<List<List<List<Double>>>>): GeoPointsNetwork {
        return GeoPointsNetwork(
            features = listOf(
                FeatureNetwork(
                    geometry = GeometryNetwork(coordinates),
                ),
            ),
        )
    }

    private fun getNotEmptyCoordinates(): List<List<List<List<Double>>>> {
        return listOf(
            listOf(listOf(listOf(0.0, 0.0), listOf(1.0, 2.0), listOf(3.0, 4.0), listOf(4.0, 8.0))),
            listOf(listOf(listOf(10.0, 0.0), listOf(23.0, 10.0), listOf(34.0, 6.0))),
            listOf(listOf(listOf(0.0, 0.0), listOf(1.0, 2.0), listOf(3.0, 4.0), listOf(4.0, 8.0))),
        )
    }

    private fun getEmptyCoordinates(): List<List<List<List<Double>>>> {
        return listOf(
            listOf(emptyList()),
            emptyList(),
        )
    }
}
package com.degalex.waadsutest.interactors

import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.interactors.implementation.GetIslandLengthInteractorDefault
import com.degalex.waadsutest.domain.interactors.implementation.GetTerritoryLengthInteractorDefault
import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.assertTrue
import org.junit.Test

class GetTerritoryLengthInteractorTest {

    private val getIslandLengthInteractor = GetIslandLengthInteractorDefault()
    private val getTerritoryLengthInteractor =
        GetTerritoryLengthInteractorDefault(getIslandLengthInteractor)

    @Test
    fun getTerritoryLengthInteractor_emptyIslands_territoryLengthZero() {
        assertTrue(getTerritoryLengthInteractor(getTerritoryWithEmptyIslands()) == 0)
    }

    @Test
    fun getTerritoryLengthInteractor_emptyCoordinates_territoryLengthZero() {
        assertTrue(getTerritoryLengthInteractor(getTerritoryWithAllEmptyCoordinates()) == 0)
    }

    @Test
    fun getTerritoryLengthInteractor_allEmptyCoordinates_territoryLengthAboveZero() {
        assertTrue(getTerritoryLengthInteractor(getTerritoryWithOneEmptyCoordinates()) > 0)
    }

    @Test
    fun getTerritoryLengthInteractor_oneEmptyCoordinates_territoryLengthAboveZero() {
        assertTrue(getTerritoryLengthInteractor(getTerritoryWithOneEmptyCoordinates()) > 0)
    }

    @Test
    fun getTerritoryLengthInteractor_invalidCoordinates_territoryLengthZero() {
        assertTrue(getTerritoryLengthInteractor(getTerritoryWithInvalidCoordinates()) == 0)
    }

    @Test
    fun getTerritoryLengthInteractor_validCoordinates_territoryLengthAboveZero() {
        assertTrue(getTerritoryLengthInteractor(getTerritoryValidCoordinates()) > 0)
    }

    private fun getTerritoryWithEmptyIslands(): Territory {
        return Territory(
            islands = emptyList(),
        )
    }

    private fun getTerritoryWithAllEmptyCoordinates(): Territory {
        return Territory(
            islands = listOf(
                Island(coordinates = emptyList()),
                Island(coordinates = emptyList()),
                Island(coordinates = emptyList()),
            ),
        )
    }

    private fun getTerritoryWithOneEmptyCoordinates(): Territory {
        return Territory(
            islands = listOf(
                Island(coordinates = emptyList()),
                Island(coordinates = listOf(LatLng(1.0, 0.0), LatLng(17.0, 2.0))),
                Island(coordinates = listOf(LatLng(50.0, 40.0), LatLng(13.0, 2.0))),
            ),
        )
    }

    private fun getTerritoryWithInvalidCoordinates(): Territory {
        return Territory(
            islands = listOf(
                Island(coordinates = emptyList()),
                Island(coordinates = listOf(LatLng(2300.0, -9000.0))),
            ),
        )
    }

    private fun getTerritoryValidCoordinates(): Territory {
        return Territory(
            islands = listOf(
                Island(coordinates = listOf(LatLng(23.0, -9.0), LatLng(100.0, 6.0))),
                Island(coordinates = listOf(LatLng(1.0, 0.0), LatLng(17.0, 2.0))),
                Island(coordinates = listOf(LatLng(50.0, 40.0), LatLng(13.0, 2.0))),
            ),
        )
    }
}
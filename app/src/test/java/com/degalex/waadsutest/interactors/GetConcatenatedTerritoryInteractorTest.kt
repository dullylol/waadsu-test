package com.degalex.waadsutest.interactors

import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.interactors.implementation.GetConcatenatedTerritoryInteractorDefault
import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GetConcatenatedTerritoryInteractorTest {

    private val getConcatenatedTerritoryInteractor = GetConcatenatedTerritoryInteractorDefault()

    @Test
    fun getConcatenatedTerritoryInteractor_shouldConcat() {
        val territory = getShouldConcatTerritory()
        val concatenatedTerritory = getConcatenatedTerritoryInteractor(territory)

        assertNotEquals(concatenatedTerritory.islands.size, territory.islands.size)
    }

    @Test
    fun getConcatenatedTerritoryInteractor_notShouldConcat() {
        val territory = getNotShouldConcatTerritory()
        val concatenatedTerritory = getConcatenatedTerritoryInteractor(territory)

        assertEquals(concatenatedTerritory.islands.size, territory.islands.size)
    }

    private fun getShouldConcatTerritory(): Territory {
        return Territory(
            islands = listOf(
                Island(
                    coordinates = listOf(
                        LatLng(0.0, 0.0),
                        LatLng(0.0, 20.0),
                        LatLng(20.0, 20.0),
                        LatLng(20.0, 0.0),
                    ),
                ),
                Island(
                    coordinates = listOf(
                        LatLng(20.0, 20.0),
                        LatLng(30.0, 10.0),
                        LatLng(20.0, 0.0),
                    ),
                ),
            ),
        )
    }

    private fun getNotShouldConcatTerritory(): Territory {
        return Territory(
            islands = listOf(
                Island(
                    coordinates = listOf(
                        LatLng(0.0, 0.0),
                        LatLng(0.0, 20.0),
                        LatLng(20.0, 20.0),
                        LatLng(20.0, 0.0),
                    ),
                ),
                Island(
                    coordinates = listOf(
                        LatLng(30.0, 20.0),
                        LatLng(40.0, 10.0),
                        LatLng(30.0, 0.0),
                    ),
                ),
            ),
        )
    }
}
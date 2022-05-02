package com.degalex.waadsutest.interactors

import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.interactors.implementation.GetIslandLengthInteractorDefault
import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.assertTrue
import org.junit.Test

class GetIslandLengthInteractorTest {

    private val getIslandLengthInteractor = GetIslandLengthInteractorDefault()

    @Test
    fun getIslandLengthInteractor_coordinatesEmpty_islandLengthZero() {
        assertTrue(getIslandLengthInteractor(getIslandWithEmptyCoordinates()) == 0)
    }

    @Test
    fun getIslandLengthInteractor_oneCoordinate_islandLengthZero() {
        assertTrue(getIslandLengthInteractor(getIslandWithOneCoordinate()) == 0)
    }

    @Test
    fun getIslandLengthInteractor_manyCoordinates_islandLengthAboveZero() {
        assertTrue(getIslandLengthInteractor(getIslandWithManyCoordinate()) > 0)
    }

    private fun getIslandWithEmptyCoordinates(): Island {
        return Island(coordinates = emptyList())
    }

    private fun getIslandWithOneCoordinate(): Island {
        return Island(coordinates = listOf(LatLng(0.0, 0.0)))
    }

    private fun getIslandWithManyCoordinate(): Island {
        return Island(
            coordinates = listOf(
                LatLng(0.0, 0.0),
                LatLng(5.0, 5.0),
                LatLng(10.0, 7.0),
                LatLng(50.0, 3.0),
                LatLng(11.0, -5.0),
                LatLng(2.0, -20.0),
            )
        )
    }
}
package com.degalex.waadsutest.viewmodels

import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.interactors.implementation.GetConcatenatedTerritoryInteractorDefault
import com.degalex.waadsutest.domain.interactors.implementation.GetIslandLengthInteractorDefault
import com.degalex.waadsutest.domain.interactors.implementation.GetTerritoryLengthInteractorDefault
import com.degalex.waadsutest.domain.interactors.interfaces.GetTerritoryInteractor
import com.degalex.waadsutest.ui.screens.map.MapViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MapViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockGetTerritoryInteractor = mock<GetTerritoryInteractor>()
    private val getConcatenatedTerritoryInteractor = GetConcatenatedTerritoryInteractorDefault()

    private val getIslandLengthInteractor = GetIslandLengthInteractorDefault()
    private val getTerritoryLengthInteractor =
        GetTerritoryLengthInteractorDefault(getIslandLengthInteractor)

    private val mapViewModel = MapViewModel(
        mockGetTerritoryInteractor,
        getConcatenatedTerritoryInteractor,
        getTerritoryLengthInteractor,
        getIslandLengthInteractor,
        testDispatcher,
        testDispatcher,
    )

    @Test
    fun mapViewModel_onMapReady_loadTerritory() = runTest(testDispatcher) {
        val territory = getMockTerritory()
        val territoryList = mutableListOf<Territory>()

        whenever(mockGetTerritoryInteractor()).thenReturn(territory)

        val job = launch {
            mapViewModel.coordinatesStateFlow.toList(territoryList)
        }

        mapViewModel.onMapReady()

        print(territoryList)

        assertTrue(territoryList.last().islands.isNotEmpty())
        assertTrue(mapViewModel.territoryLengthStateFlow.value > 0)

        job.cancel()
    }

    @Test
    fun mapViewModel_onIslandClicked() = runTest(testDispatcher) {
        val island = getFirstMockIsland()
        val selectedIslandLengthList = mutableListOf<Int>()

        val job = launch {
            mapViewModel.selectedIslandLengthStateFlow.toList(selectedIslandLengthList)
        }

        mapViewModel.onIslandClicked(island)

        assertTrue(selectedIslandLengthList.last() > 0)

        job.cancel()
    }

    @Test
    fun mapViewModel_onAnotherIslandClicked() = runTest(testDispatcher) {
        val firstIsland = getFirstMockIsland()
        val secondIsland = getSecondMockIsland()
        val selectedIslandLengthList = mutableListOf<Int>()

        val job = launch {
            mapViewModel.selectedIslandLengthStateFlow.toList(selectedIslandLengthList)
        }

        mapViewModel.onIslandClicked(firstIsland)
        mapViewModel.onIslandClicked(secondIsland)

        assertTrue(selectedIslandLengthList.last() > 0)

        job.cancel()
    }

    private fun getMockTerritory(): Territory {
        return Territory(
            islands = listOf(
                Island(coordinates = listOf(LatLng(23.0, -9.0), LatLng(100.0, 6.0))),
                Island(coordinates = listOf(LatLng(1.0, 0.0), LatLng(17.0, 2.0))),
                Island(coordinates = listOf(LatLng(50.0, 40.0), LatLng(13.0, 2.0))),
            ),
        )
    }

    private fun getFirstMockIsland(): Island {
        return Island(
            coordinates = listOf(
                LatLng(1.0, 2.0),
                LatLng(10.0, 10.0),
                LatLng(0.0, 10.0),
            )
        )
    }

    private fun getSecondMockIsland(): Island {
        return Island(
            coordinates = listOf(
                LatLng(0.0, 0.0),
                LatLng(0.0, 10.0),
                LatLng(10.0, 10.0),
                LatLng(10.0, 0.0),
            )
        )
    }
}
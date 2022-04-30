package com.degalex.waadsutest.ui.screens.map

import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.interactors.GetConcatenatedTerritoryInteractor
import com.degalex.waadsutest.domain.interactors.GetIslandLengthInteractor
import com.degalex.waadsutest.domain.interactors.GetTerritoryInteractor
import com.degalex.waadsutest.domain.interactors.GetTerritoryLengthInteractor
import com.degalex.waadsutest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getTerritoryInteractor: GetTerritoryInteractor,
    private val getConcatenatedTerritoryInteractor: GetConcatenatedTerritoryInteractor,
    private val getTerritoryLengthInteractor: GetTerritoryLengthInteractor,
    private val getIslandLengthInteractor: GetIslandLengthInteractor,
) : BaseViewModel() {

    private val coordinatesMutableStateFlow = MutableStateFlow(Territory(emptyList()))
    val coordinatesStateFlow = coordinatesMutableStateFlow.asStateFlow()

    private val territoryLengthMutableStateFlow = MutableStateFlow(0)
    val territoryLengthStateFlow = territoryLengthMutableStateFlow.asStateFlow()

    private val selectedIslandLengthMutableStateFlow = MutableStateFlow(0)
    val selectedIslandLengthStateFlow = selectedIslandLengthMutableStateFlow.asStateFlow()

    fun onMapReady() {

        launchWithLoading {
            val territory = getTerritoryInteractor()
            val concatenatedTerritoryDeferred = async(Dispatchers.Default) {
                getConcatenatedTerritoryInteractor(territory)
            }
            val concatenatedTerritory = concatenatedTerritoryDeferred.await()

            coordinatesMutableStateFlow.value = concatenatedTerritory
            territoryLengthMutableStateFlow.value =
                getTerritoryLengthInteractor(concatenatedTerritory)
        }
    }

    fun onIslandClicked(island: Island?) {
        launch(Dispatchers.Default) {
            selectedIslandLengthMutableStateFlow.value = getIslandLengthInteractor(island)
        }
    }
}
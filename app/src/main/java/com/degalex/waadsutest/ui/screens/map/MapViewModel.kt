package com.degalex.waadsutest.ui.screens.map

import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.interactors.GetConcatenatedTerritoryInteractor
import com.degalex.waadsutest.domain.interactors.GetTerritoryInteractor
import com.degalex.waadsutest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getTerritoryInteractor: GetTerritoryInteractor,
    private val getConcatenatedTerritoryInteractor: GetConcatenatedTerritoryInteractor,
) : BaseViewModel() {

    private val coordinatesMutableStateFlow = MutableStateFlow(Territory(emptyList()))
    val coordinatesStateFlow = coordinatesMutableStateFlow.asStateFlow()

    fun onMapReady() {
        launch {
            val territory = getTerritoryInteractor()

            withContext(Dispatchers.Default) {
                coordinatesMutableStateFlow.value = getConcatenatedTerritoryInteractor(territory)
            }
        }
    }
}
package com.degalex.waadsutest.ui.screens.map

import com.degalex.waadsutest.di.utils.annotations.qualifiers.dispatchers.DefaultDispatcher
import com.degalex.waadsutest.di.utils.annotations.qualifiers.dispatchers.IODispatcher
import com.degalex.waadsutest.di.utils.annotations.qualifiers.interactors.Default
import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.interactors.interfaces.GetConcatenatedTerritoryInteractor
import com.degalex.waadsutest.domain.interactors.interfaces.GetIslandLengthInteractor
import com.degalex.waadsutest.domain.interactors.interfaces.GetTerritoryInteractor
import com.degalex.waadsutest.domain.interactors.interfaces.GetTerritoryLengthInteractor
import com.degalex.waadsutest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    @Default private val getTerritoryInteractor: GetTerritoryInteractor,
    @Default private val getConcatenatedTerritoryInteractor: GetConcatenatedTerritoryInteractor,
    @Default private val getTerritoryLengthInteractor: GetTerritoryLengthInteractor,
    @Default private val getIslandLengthInteractor: GetIslandLengthInteractor,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    private val coordinatesMutableStateFlow = MutableStateFlow(Territory(emptyList()))
    val coordinatesStateFlow = coordinatesMutableStateFlow.asStateFlow()

    private val territoryLengthMutableStateFlow = MutableStateFlow(0)
    val territoryLengthStateFlow = territoryLengthMutableStateFlow.asStateFlow()

    private val selectedIslandLengthMutableStateFlow = MutableStateFlow(0)
    val selectedIslandLengthStateFlow = selectedIslandLengthMutableStateFlow.asStateFlow()

    private val loadingMutableStateFlow = MutableStateFlow(false)
    val loadingStateFlow = loadingMutableStateFlow.asStateFlow()

    override fun onCoroutineError(throwable: Throwable) {
        super.onCoroutineError(throwable)

        loadingMutableStateFlow.value = false
    }

    fun onMapReady() {
        if (coordinatesStateFlow.value.islands.isNotEmpty()) {
            return
        }

        launchSafelyWithLoading(ioDispatcher, loadingMutableStateFlow) {
            val territory = getTerritoryInteractor()
            val concatenatedTerritoryDeferred = async(defaultDispatcher) {
                getConcatenatedTerritoryInteractor(territory)
            }
            val concatenatedTerritory = concatenatedTerritoryDeferred.await()

            coordinatesMutableStateFlow.value = concatenatedTerritory
            territoryLengthMutableStateFlow.value =
                getTerritoryLengthInteractor(concatenatedTerritory)
        }
    }

    fun onIslandClicked(island: Island?) {
        launchSafely(defaultDispatcher) {
            selectedIslandLengthMutableStateFlow.value = getIslandLengthInteractor(island)
        }
    }
}
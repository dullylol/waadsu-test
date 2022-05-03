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

    // StateFlow для координат, по которым происходит отрисовка
    private val coordinatesMutableStateFlow = MutableStateFlow(Territory(emptyList()))
    val coordinatesStateFlow = coordinatesMutableStateFlow.asStateFlow()

    // StateFlow для периметра всей территории
    private val territoryLengthMutableStateFlow = MutableStateFlow(0)
    val territoryLengthStateFlow = territoryLengthMutableStateFlow.asStateFlow()

    // StateFlow для периметра выбранного учатска территории
    private val selectedIslandLengthMutableStateFlow = MutableStateFlow(0)
    val selectedIslandLengthStateFlow = selectedIslandLengthMutableStateFlow.asStateFlow()

    // StateFlow для загрузки
    private val loadingMutableStateFlow = MutableStateFlow(false)
    val loadingStateFlow = loadingMutableStateFlow.asStateFlow()

    // Переопределенный метод обработки ошибок в корутине
    override fun onCoroutineError(throwable: Throwable) {
        super.onCoroutineError(throwable)

        loadingMutableStateFlow.value = false
    }

    // Начальные действия при создании карты: получение нужных точек и периметра
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

    // Обработка нажатия на какой-то отдельный регион
    fun onIslandClicked(island: Island?) {
        launchSafely(defaultDispatcher) {
            selectedIslandLengthMutableStateFlow.value = getIslandLengthInteractor(island)
        }
    }
}
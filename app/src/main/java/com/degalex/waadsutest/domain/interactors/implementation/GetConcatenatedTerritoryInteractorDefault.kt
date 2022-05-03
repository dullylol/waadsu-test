package com.degalex.waadsutest.domain.interactors.implementation

import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.extensions.distanceTo
import com.degalex.waadsutest.domain.interactors.interfaces.GetConcatenatedTerritoryInteractor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import javax.inject.Inject
import kotlin.math.abs

// Класс для работы с бизнес-логикой - получение соедененных островов
class GetConcatenatedTerritoryInteractorDefault @Inject constructor(
) : GetConcatenatedTerritoryInteractor {

    override operator fun invoke(territory: Territory): Territory {
        return getConcatenatedTerritory(territory)
    }

    private fun getConcatenatedTerritory(territory: Territory): Territory {
        val concatenatedIslands = territory.islands.toMutableList()

        // Сравниваем каждый остров с каждым
        concatenatedIslands.forEachIndexed { island1Index, island1 ->
            concatenatedIslands.forEachIndexed { island2Index, island2 ->

                // Находим острова, которые расположены рядом
                if (island1Index != island2Index && areIslandsNear(island1, island2)) {

                    // Индексы точек островов, которые нужно удалить для их соединения
                    val island1IndexesToRemove = mutableSetOf<Int>()
                    val island2IndexesToRemove = mutableSetOf<Int>()

                    val island1Coordinates = island1.coordinates.toMutableList()
                    val island2Coordinates = island2.coordinates.toMutableList()

                    // Ищем точки, которые расположены рядом и добавляем в лист для удаления
                    island1Coordinates.forEachIndexed { index1, latLng1 ->
                        island2Coordinates.forEachIndexed { index2, latLng2 ->
                            if (latLng1 distanceTo latLng2 < DISTANCE_ERROR) {
                                island1IndexesToRemove.add(index1)
                                island2IndexesToRemove.add(index2)
                            }
                        }
                    }

                    if (island1IndexesToRemove.isNotEmpty()) {

                        // Находим начальные точки островов, от куда произведется удаления, и из
                        // которых нужно будет соеденять оставшиеся точки островов
                        val startConcatPoint1Index = island1IndexesToRemove.minOrNull()!!
                        val startConcatPoint2Index = island2IndexesToRemove.minOrNull()!!

                        // Лист для правильного порядка точек второго остова
                        val orderedIsland2Coordinates = mutableListOf<LatLng>()

                        // Удаляем токи с конца по индексу, чтоб индексы элементов не сместились
                        for (index in island1IndexesToRemove.sortedDescending()) {
                            island1Coordinates.removeAt(index)
                        }

                        // Удаляем индексы у второго острова
                        for (index in island2IndexesToRemove.sortedDescending()) {
                            island2Coordinates.removeAt(index)
                        }

                        // Нахождение правильного порядка точек второго острова
                        for (i in startConcatPoint2Index until island2Coordinates.size) {
                            orderedIsland2Coordinates.add(island2Coordinates[i])
                        }

                        for (i in 0 until startConcatPoint2Index) {
                            orderedIsland2Coordinates.add(island2Coordinates[i])
                        }

                        // Добаление точек второго острова к точкам первого
                        island1Coordinates.addAll(startConcatPoint1Index, orderedIsland2Coordinates)

                        // Первый остров обновляем в листе всех островов
                        concatenatedIslands[island1Index] = concatenatedIslands[island1Index].copy(
                            coordinates = island1Coordinates,
                        )

                        // Второй остров очищаем, чтоб потом удалить
                        concatenatedIslands[island2Index] = concatenatedIslands[island2Index].copy(
                            coordinates = emptyList(),
                        )
                    }
                }
            }
        }

        // Удаляем пустые острова
        concatenatedIslands.removeAll { it.coordinates.isEmpty() }

        return Territory(concatenatedIslands)
    }

    // Поиск точек, которые расположены рядом
    private fun areIslandsNear(island1: Island, island2: Island): Boolean {
        if (island1.coordinates.isEmpty() || island2.coordinates.isEmpty()) {
            return false
        }

        val island1BoundsBuilder = LatLngBounds.Builder()
        val island2BoundsBuilder = LatLngBounds.Builder()

        island1.coordinates.forEach(island1BoundsBuilder::include)
        island2.coordinates.forEach(island2BoundsBuilder::include)

        val island1Bounds = island1BoundsBuilder.build()
        val island2Bounds = island2BoundsBuilder.build()

        return abs(island1Bounds.northeast.longitude - island2Bounds.southwest.longitude) < DISTANCE_ERROR
                || abs(island1Bounds.northeast.latitude - island2Bounds.southwest.latitude) < DISTANCE_ERROR
                || abs(island1Bounds.southwest.longitude - island2Bounds.northeast.longitude) < DISTANCE_ERROR
                || abs(island1Bounds.southwest.latitude - island2Bounds.northeast.latitude) < DISTANCE_ERROR
    }

    companion object {

        // Погрешность, при которой считается, что точки находятся рядом
        private const val DISTANCE_ERROR = 0.001
    }
}
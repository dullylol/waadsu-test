package com.degalex.waadsutest.domain.interactors.implementation

import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.extensions.distanceTo
import com.degalex.waadsutest.domain.interactors.interfaces.GetConcatenatedTerritoryInteractor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import javax.inject.Inject
import kotlin.math.abs

class GetConcatenatedTerritoryInteractorDefault @Inject constructor(
) : GetConcatenatedTerritoryInteractor {

    override operator fun invoke(territory: Territory): Territory {
        return getConcatenatedTerritory(territory)
    }

    private fun getConcatenatedTerritory(territory: Territory): Territory {
        val concatenatedIslands = territory.islands.toMutableList()

        concatenatedIslands.forEachIndexed { island1Index, island1 ->
            concatenatedIslands.forEachIndexed { island2Index, island2 ->

                if (island1Index != island2Index && areIslandsNear(island1, island2)) {

                    val island1IndexesToRemove = mutableSetOf<Int>()
                    val island2IndexesToRemove = mutableSetOf<Int>()

                    val island1Coordinates = island1.coordinates.toMutableList()
                    val island2Coordinates = island2.coordinates.toMutableList()

                    island1Coordinates.forEachIndexed { index1, latLng1 ->
                        island2Coordinates.forEachIndexed { index2, latLng2 ->
                            if (latLng1 distanceTo latLng2 < DISTANCE_ERROR) {
                                island1IndexesToRemove.add(index1)
                                island2IndexesToRemove.add(index2)
                            }
                        }
                    }

                    if (island1IndexesToRemove.isNotEmpty()) {

                        val startConcatPoint1Index = island1IndexesToRemove.minOrNull()!!
                        val startConcatPoint2Index = island2IndexesToRemove.minOrNull()!!

                        val orderedIsland2Coordinates = mutableListOf<LatLng>()

                        for (index in island1IndexesToRemove.sortedDescending()) {
                            island1Coordinates.removeAt(index)
                        }

                        for (index in island2IndexesToRemove.sortedDescending()) {
                            island2Coordinates.removeAt(index)
                        }

                        for (i in startConcatPoint2Index until island2Coordinates.size) {
                            orderedIsland2Coordinates.add(island2Coordinates[i])
                        }

                        for (i in 0 until startConcatPoint2Index) {
                            orderedIsland2Coordinates.add(island2Coordinates[i])
                        }

                        island1Coordinates.addAll(startConcatPoint1Index, orderedIsland2Coordinates)

                        concatenatedIslands[island1Index] = concatenatedIslands[island1Index].copy(
                            coordinates = island1Coordinates,
                        )

                        concatenatedIslands[island2Index] = concatenatedIslands[island2Index].copy(
                            coordinates = emptyList(),
                        )
                    }
                }
            }
        }

        concatenatedIslands.removeAll { it.coordinates.isEmpty() }

        return Territory(concatenatedIslands)
    }

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

        private const val DISTANCE_ERROR = 0.001
    }
}
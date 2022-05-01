package com.degalex.waadsutest.domain.mappers

import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class TerritoryMapper @Inject constructor() {

    fun toDomain(coordinatesNetwork: List<List<List<List<Double>>>>): Territory {
        return Territory(
            islands = coordinatesNetwork.map { islandCoordinates ->
                Island(
                    coordinates = islandCoordinates.flatten().map { coordinates ->
                        LatLng(coordinates[1], coordinates[0])
                    }
                )
            }
        )
    }
}
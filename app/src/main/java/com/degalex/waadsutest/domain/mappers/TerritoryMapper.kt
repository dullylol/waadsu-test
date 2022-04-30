package com.degalex.waadsutest.domain.mappers

import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
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
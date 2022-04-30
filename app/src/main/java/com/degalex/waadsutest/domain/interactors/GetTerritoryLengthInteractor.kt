package com.degalex.waadsutest.domain.interactors

import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.extesions.distanceToInMeters
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetTerritoryLengthInteractor @Inject constructor() {

    operator fun invoke(territory: Territory): Int {
        var territoryLength = 0.0

        territory.islands.forEach { island ->
            val coordinates = island.coordinates

            for (islandIndex in 0 until coordinates.size - 1) {
                territoryLength += coordinates[islandIndex] distanceToInMeters coordinates[islandIndex + 1]
            }

            territoryLength += coordinates[0] distanceToInMeters coordinates[coordinates.size - 1]
        }

        return (territoryLength / 1000).toInt()
    }
}
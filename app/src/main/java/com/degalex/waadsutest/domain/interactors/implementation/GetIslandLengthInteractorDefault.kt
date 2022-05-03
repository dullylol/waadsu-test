package com.degalex.waadsutest.domain.interactors.implementation

import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.extensions.distanceToInMeters
import com.degalex.waadsutest.domain.interactors.interfaces.GetIslandLengthInteractor
import javax.inject.Inject

// Класс для работы с бизнес-логикой - получение периметра острова
class GetIslandLengthInteractorDefault @Inject constructor() : GetIslandLengthInteractor {

    override operator fun invoke(island: Island?): Int {
        if (island == null || island.coordinates.isEmpty()) {
            return 0
        }

        var territoryLength = 0.0
        val coordinates = island.coordinates

        for (islandIndex in 0 until coordinates.size - 1) {
            territoryLength += coordinates[islandIndex] distanceToInMeters coordinates[islandIndex + 1]
        }

        territoryLength += coordinates[0] distanceToInMeters coordinates[coordinates.size - 1]

        return (territoryLength / 1000).toInt()
    }
}
package com.degalex.waadsutest.domain.interactors.implementation

import com.degalex.waadsutest.di.utils.annotations.qualifiers.interactors.Default
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.interactors.interfaces.GetIslandLengthInteractor
import com.degalex.waadsutest.domain.interactors.interfaces.GetTerritoryLengthInteractor
import javax.inject.Inject

class GetTerritoryLengthInteractorDefault @Inject constructor(
    @Default private val getIslandLengthInteractor: GetIslandLengthInteractor,
) : GetTerritoryLengthInteractor {

    override operator fun invoke(territory: Territory): Int {
        return territory.islands.filter { it.coordinates.isNotEmpty() }.sumOf { island ->
            getIslandLengthInteractor(island)
        }
    }
}
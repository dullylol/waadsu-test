package com.degalex.waadsutest.domain.interactors.interfaces

import com.degalex.waadsutest.domain.entities.Territory

interface GetConcatenatedTerritoryInteractor {

    operator fun invoke(territory: Territory): Territory
}
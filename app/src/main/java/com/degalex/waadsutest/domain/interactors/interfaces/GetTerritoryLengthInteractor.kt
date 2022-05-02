package com.degalex.waadsutest.domain.interactors.interfaces

import com.degalex.waadsutest.domain.entities.Territory

interface GetTerritoryLengthInteractor {

    operator fun invoke(territory: Territory): Int
}
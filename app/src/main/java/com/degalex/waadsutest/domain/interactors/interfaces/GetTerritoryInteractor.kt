package com.degalex.waadsutest.domain.interactors.interfaces

import com.degalex.waadsutest.domain.entities.Territory

interface GetTerritoryInteractor {

    suspend operator fun invoke(): Territory
}
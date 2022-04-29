package com.degalex.waadsutest.domain.interactors

import com.degalex.waadsutest.data.remote.repositories.CoordinatesRepository
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.mappers.TerritoryMapper
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetTerritoryInteractor @Inject constructor(
    private val coordinatesRepository: CoordinatesRepository,
    private val territoryMapper: TerritoryMapper,
) {

    suspend operator fun invoke(): Territory {
        return territoryMapper.toDomain(coordinatesRepository.getCoordinates())
    }
}
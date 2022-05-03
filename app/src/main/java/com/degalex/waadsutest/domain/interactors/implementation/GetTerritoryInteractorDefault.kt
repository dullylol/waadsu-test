package com.degalex.waadsutest.domain.interactors.implementation

import com.degalex.waadsutest.data.repositories.interfaces.CoordinatesRepository
import com.degalex.waadsutest.di.utils.annotations.qualifiers.repositories.Network
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.domain.exceptions.CoordinatesInvalidExceptions
import com.degalex.waadsutest.domain.interactors.interfaces.GetTerritoryInteractor
import com.degalex.waadsutest.domain.mappers.TerritoryMapper
import javax.inject.Inject

// Класс для работы с бизнес-логикой - получение всех островов из апишки и пробразование данных
// в удобные сущности
class GetTerritoryInteractorDefault @Inject constructor(
    @Network private val coordinatesRepository: CoordinatesRepository,
    private val territoryMapper: TerritoryMapper,
) : GetTerritoryInteractor {

    @Throws(CoordinatesInvalidExceptions::class)
    override suspend operator fun invoke(): Territory {
        val coordinates = coordinatesRepository.getCoordinates()

        if (isCoordinatesValid(coordinates)) {
            return territoryMapper.toDomain(coordinatesRepository.getCoordinates())
        } else {
            throw CoordinatesInvalidExceptions()
        }
    }

    private fun isCoordinatesValid(coordinates: List<List<List<List<Double>>>>): Boolean {
        return coordinates.flatten().flatten().none { it.size != 2 }
    }
}
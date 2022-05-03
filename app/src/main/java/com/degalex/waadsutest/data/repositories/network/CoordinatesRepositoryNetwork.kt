package com.degalex.waadsutest.data.repositories.network

import com.degalex.waadsutest.data.datasources.network.WaadsuApiCalls
import com.degalex.waadsutest.data.repositories.interfaces.CoordinatesRepository
import javax.inject.Inject

// Класс для работы с датасорсами
class CoordinatesRepositoryNetwork @Inject constructor(
    private val waadsuApiCalls: WaadsuApiCalls,
) : CoordinatesRepository {

    // Получение координат с апишки
    override suspend fun getCoordinates(): List<List<List<List<Double>>>> {
        return waadsuApiCalls.getGeoPoints().features?.firstOrNull()?.geometry?.coordinates
            ?: emptyList()
    }
}
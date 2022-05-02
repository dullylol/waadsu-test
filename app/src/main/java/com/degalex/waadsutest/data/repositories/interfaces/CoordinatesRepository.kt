package com.degalex.waadsutest.data.repositories.interfaces

interface CoordinatesRepository {

    suspend fun getCoordinates(): List<List<List<List<Double>>>>
}
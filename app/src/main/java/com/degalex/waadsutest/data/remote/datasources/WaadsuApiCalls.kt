package com.degalex.waadsutest.data.remote.datasources

import com.degalex.waadsutest.data.remote.entities.GeoPointsNetwork
import retrofit2.http.GET

interface WaadsuApiCalls {

    @GET("russia.geo.json")
    suspend fun getGeoPoints(): GeoPointsNetwork
}
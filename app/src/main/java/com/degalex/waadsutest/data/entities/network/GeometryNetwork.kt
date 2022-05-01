package com.degalex.waadsutest.data.entities.network

import com.google.gson.annotations.SerializedName

data class GeometryNetwork(
    @SerializedName("coordinates") val coordinates: List<List<List<List<Double>>>>?,
)
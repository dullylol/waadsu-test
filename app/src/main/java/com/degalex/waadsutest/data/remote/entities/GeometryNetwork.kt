package com.degalex.waadsutest.data.remote.entities

import com.google.gson.annotations.SerializedName

data class GeometryNetwork(
    @SerializedName("coordinates") val coordinates: List<List<List<List<Double>>>>?,
)
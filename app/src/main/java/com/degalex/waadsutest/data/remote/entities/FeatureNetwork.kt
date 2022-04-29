package com.degalex.waadsutest.data.remote.entities

import com.google.gson.annotations.SerializedName

data class FeatureNetwork(
    @SerializedName("geometry") val geometry: GeometryNetwork?,
)

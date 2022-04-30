package com.degalex.waadsutest.domain.extesions

import com.google.android.gms.maps.model.LatLng
import kotlin.math.*

infix fun LatLng.distanceTo(latLng: LatLng): Double {
    return sqrt((latitude - latLng.latitude).pow(2.0)
            + (longitude - latLng.longitude).pow(2.0))
}

infix fun LatLng.distanceToInMeters(latLng: LatLng): Double {
    val earthRadius = 6378.137
    val dLat = latLng.latitude * Math.PI / 180 - latitude * Math.PI / 180;
    val dLon = latLng.longitude * Math.PI / 180 - longitude * Math.PI / 180;
    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(latitude * Math.PI / 180) * cos(latLng.latitude * Math.PI / 180) *
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    val d = earthRadius * c

    return d * 1000
}
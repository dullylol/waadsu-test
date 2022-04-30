package com.degalex.waadsutest.ui.screens.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.degalex.waadsutest.R
import com.degalex.waadsutest.databinding.FragmentMapBinding
import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    private val mapViewModel: MapViewModel by viewModels()

    private lateinit var googleMap: GoogleMap

    private var lastSelectedPolygon: Polygon? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(::onMapReady)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {

            mapViewModel.loadingStateFlow.collect { loading ->

                binding.progressIndicator.isVisible = loading
            }
        }

        lifecycleScope.launchWhenCreated {

            mapViewModel.errorStateFlow.collect { throwable ->

                if (throwable != null) {
                    Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launchWhenCreated {

            mapViewModel.coordinatesStateFlow.collect { territory ->

                animateCameraToTerritory(territory)
                addTerritoryPolygons(territory)
            }
        }

        lifecycleScope.launchWhenCreated {

            mapViewModel.territoryLengthStateFlow.collect { territoryLength ->

                binding.territoryLengthTextView.text =
                    getString(R.string.format_territory_length, territoryLength)
            }
        }

        lifecycleScope.launchWhenCreated {

            mapViewModel.selectedIslandLengthStateFlow.collect { islandLength ->

                binding.selectedIslandLengthTextView.text =
                    getString(R.string.format_selected_territory_length, islandLength)
            }
        }
    }

    private fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        mapViewModel.onMapReady()

        googleMap.setOnPolygonClickListener { polygon ->
            selectPolygon(polygon)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        binding.mapView.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        binding.mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        binding.mapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    private fun animateCameraToTerritory(territory: Territory) {
        if (territory.islands.isEmpty()) {
            return
        }

        val boundsBuilder = LatLngBounds.Builder()

        territory.islands.maxByOrNull { it.coordinates.size }?.coordinates?.forEach(boundsBuilder::include)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 0))
    }

    private fun addTerritoryPolygons(territory: Territory) {
        territory.islands.forEach { island ->
            val polygonOptions = PolygonOptions()
                .addAll(island.coordinates)
                .strokeWidth(5f)

            googleMap.addPolygon(polygonOptions).isClickable = true
        }
    }

    private fun selectPolygon(polygon: Polygon) {
        lastSelectedPolygon?.fillColor = Color.TRANSPARENT
        lastSelectedPolygon?.strokeColor = Color.BLACK

        if (polygon != lastSelectedPolygon) {
            polygon.fillColor = Color.CYAN
            polygon.strokeColor = Color.RED

            mapViewModel.onIslandClicked(Island(coordinates = polygon.points))

            lastSelectedPolygon = polygon
        } else {
            mapViewModel.onIslandClicked(null)

            lastSelectedPolygon = null
        }
    }

    companion object {

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}
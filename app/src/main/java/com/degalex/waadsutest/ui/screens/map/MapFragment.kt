package com.degalex.waadsutest.ui.screens.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.degalex.waadsutest.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
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

            mapViewModel.coordinatesStateFlow.collect { territory ->

                territory.islands.sortedByDescending { it.coordinates.size }.forEach { island ->
                    val polygonOptions = PolygonOptions()
                        .addAll(island.coordinates)
                        .strokeWidth(5f)

                    googleMap.addPolygon(polygonOptions).isClickable = true
                }
            }
        }
    }

    private fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        mapViewModel.onMapReady()

        googleMap.setOnPolygonClickListener { polygon ->
            lastSelectedPolygon?.fillColor = Color.TRANSPARENT
            lastSelectedPolygon?.strokeColor = Color.BLACK

            polygon.fillColor = Color.CYAN
            polygon.strokeColor = Color.RED

            lastSelectedPolygon = polygon
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

    companion object {

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}
package com.degalex.waadsutest.ui.screens.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.degalex.waadsutest.R
import com.degalex.waadsutest.databinding.FragmentMapBinding
import com.degalex.waadsutest.domain.entities.Island
import com.degalex.waadsutest.domain.entities.Territory
import com.degalex.waadsutest.ui.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment() {

    private lateinit var binding: FragmentMapBinding

    private val mapViewModel: MapViewModel by viewModels()

    private var googleMap: GoogleMap? = null

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

    private fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        setupObservers()

        mapViewModel.onMapReady()

        googleMap.setOnPolygonClickListener { polygon ->
            onPolygonClick(polygon)
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

    // Обсерверы для всех StateFlow во вьюмодели. При смене значения StateFlow сразу же меняется UI
    private fun setupObservers() {

        mapViewModel.loadingStateFlow.collectInCoroutine { loading ->
            binding.progressIndicator.isVisible = loading
        }

        mapViewModel.errorStateFlow.collectInCoroutine { throwable ->
            if (throwable != null) {
                Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
            }
        }

        mapViewModel.coordinatesStateFlow.collectInCoroutine { territory ->
            animateCameraToTerritory(territory)
            addTerritoryPolygons(territory)
        }

        mapViewModel.territoryLengthStateFlow.collectInCoroutine { territoryLength ->
            binding.territoryLengthTextView.text =
                getString(R.string.format_territory_length, territoryLength)
        }

        mapViewModel.selectedIslandLengthStateFlow.collectInCoroutine { islandLength ->
            binding.selectedIslandLengthTextView.text =
                getString(R.string.format_selected_territory_length, islandLength)
        }
    }

    private fun animateCameraToTerritory(territory: Territory) {
        if (territory.islands.isEmpty()) {
            return
        }

        val boundsBuilder = LatLngBounds.Builder()

        territory.islands.maxByOrNull { it.coordinates.size }?.coordinates?.forEach(boundsBuilder::include)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 0))
    }

    // Отрисовка полигонов по координатам, переданным в объекте класса Territory
    private fun addTerritoryPolygons(territory: Territory) {
        territory.islands.forEach { island ->
            val polygonOptions = PolygonOptions()
                .addAll(island.coordinates)
                .strokeWidth(5f)

            googleMap?.addPolygon(polygonOptions)?.isClickable = true
        }
    }

    // Обработчик нажатия на полигон
    private fun onPolygonClick(polygon: Polygon) {
        paintPolygonInDefaultColor(lastSelectedPolygon)

        if (polygon != lastSelectedPolygon) {
            paintPolygonInRedColor(polygon)

            mapViewModel.onIslandClicked(Island(coordinates = polygon.points))

            lastSelectedPolygon = polygon
        } else {
            mapViewModel.onIslandClicked(null)

            lastSelectedPolygon = null
        }
    }

    // Заполнение полигона стандартным цветом
    private fun paintPolygonInDefaultColor(polygon: Polygon?) {
        polygon?.fillColor = Color.TRANSPARENT
        polygon?.strokeColor = Color.BLACK
    }

    // Заполнение полигона другим цветом
    private fun paintPolygonInRedColor(polygon: Polygon?) {
        polygon?.fillColor = Color.CYAN
        polygon?.strokeColor = Color.RED
    }

    companion object {

        // Создаем таким образом фрагменты для дальнейшей удобной работы с arguments
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}
package com.example.lesson_3_safarov.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_3_safarov.databinding.ActivityMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.GeoObjectSelectionMetadata

class MapActivity : AppCompatActivity() {
    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }

        const val RESULT_ADDRESS_KEY = "address_result"
        private const val START_POINT_LATITUDE = 59.968456
        private const val START_POINT_LONGITUDE = 30.315842
        private const val START_POINT_ZOOM = 17f
        private const val START_POINT_AZIMUTH = 0f
        private const val START_POINT_TILT = 0f
        private const val START_MOVING_DURATION = 2f
    }

    private lateinit var binding: ActivityMapBinding

    private val mapTapListener = GeoObjectTapListener {
        val selectionMetadata: GeoObjectSelectionMetadata = it
            .geoObject
            .metadataContainer
            .getItem(GeoObjectSelectionMetadata::class.java)
        binding.mapView.mapWindow.map.selectGeoObject(selectionMetadata)
        if (it.geoObject.name != null) {
            binding.selectedApartment.visibility = View.VISIBLE
            binding.selectedApartment.text = it.geoObject.name
        } else binding.selectedApartment.visibility = View.GONE
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(this)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()

        binding.mapView.mapWindow.map.move(
            CameraPosition(
                Point(START_POINT_LATITUDE, START_POINT_LONGITUDE),
                START_POINT_ZOOM,
                START_POINT_AZIMUTH,
                START_POINT_TILT
            ),
            Animation(Animation.Type.SMOOTH, START_MOVING_DURATION)
        ) { }
    }

    private fun setListeners() {
        binding.closeIcon.setOnClickListener {
            this.finish()
        }
        binding.selectButton.setOnClickListener {
            if (binding.selectedApartment.visibility == View.VISIBLE) {
                val resultIntent = Intent()
                resultIntent.putExtra(RESULT_ADDRESS_KEY, binding.selectedApartment.text)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
        binding.mapView.mapWindow.map.addTapListener(mapTapListener)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}
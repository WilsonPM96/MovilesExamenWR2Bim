package com.example.wilson.estudiantemateria

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_google_maps.*
import java.util.*

class GoogleMapsActivity :
        AppCompatActivity(),
        OnMapReadyCallback,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener{

    private lateinit var mMap: GoogleMap

    var arregloMarcadores = ArrayList<Marker>()

    val epnLatLang = LatLng(-0.2103764, -78.4891095)

    val zoom = 17f

    val clienteLatLang = LatLng(-0.260784, -78.538662)
    val cliente: CameraPosition = CameraPosition
            .Builder()
            .target(clienteLatLang)
            .zoom(zoom)
            .build()


    var usuarioTienePermisosLocalizacion = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        solicitarPermisosLocalizacion()


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        with(googleMap) {

            establecerListeners(googleMap)
            establecerSettings(googleMap)


            anadirMarcador(epnLatLang, "Escuela Politécnica Nacional")


            moverCamaraPorLatLongZoom(epnLatLang, zoom)


            button_cliente.setOnClickListener { v ->
                anadirMarcador(clienteLatLang, "Marcador del Cliente")
                moverCamaraPorPosicion(cliente)
            }
        }
    }

    fun solicitarPermisosLocalizacion() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            usuarioTienePermisosLocalizacion = true
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun establecerSettings(googleMap: GoogleMap) {
        with(googleMap) {
            uiSettings.isZoomControlsEnabled = false
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    private fun anadirMarcador(latitudLongitud: LatLng, titulo: String) {

        arregloMarcadores.forEach { marker: Marker ->
            marker.remove()
        }

        arregloMarcadores = ArrayList<Marker>()

        val marker = mMap.addMarker(
                MarkerOptions()
                        .position(latitudLongitud)
                        .title(titulo)
        )

        arregloMarcadores.add(marker)

        Log.i("google-map", "$arregloMarcadores")
    }


    private fun moverCamaraPorLatLongZoom(latitudLongitud: LatLng, zoom: Float) {


        mMap.moveCamera(
                CameraUpdateFactory
                        .newLatLngZoom(latitudLongitud, zoom)
        )


    }


    private fun moverCamaraPorPosicion(posicionCamara: CameraPosition) {
        mMap.moveCamera(
                CameraUpdateFactory
                        .newCameraPosition(posicionCamara)
        )
    }


    private fun establecerListeners(googleMap: GoogleMap) {
        with(googleMap) {

            setOnCameraIdleListener(this@GoogleMapsActivity)
            setOnCameraMoveStartedListener(this@GoogleMapsActivity)
            setOnCameraMoveListener(this@GoogleMapsActivity)
            setOnCameraMoveCanceledListener(this@GoogleMapsActivity)
        }
    }



    override fun onCameraMoveStarted(p0: Int) {
    }

    override fun onCameraMove() {
    }

    override fun onCameraMoveCanceled() {
    }

    override fun onCameraIdle() {
    }



}
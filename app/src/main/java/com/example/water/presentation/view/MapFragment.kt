package com.example.water.presentation.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.water.R
import com.example.water.data.model.Currently
import com.example.water.databinding.DialogLayoutBinding
import com.example.water.databinding.MapFragmentBinding
import com.example.water.presentation.viewmodel.LocationWeatherViewModel
import com.example.water.util.NetworkUtils
import com.example.water.util.State
import com.example.water.util.observe
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: MapFragmentBinding
    private val viewModel: LocationWeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.map_fragment,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.map.apply {
            onCreate(savedInstanceState)
            onResume()
            getMapAsync(this@MapFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observe(viewModel.weather) {
            when (this) {
                is State.Success -> {
                    this.data?.currently?.let {
                        showInfoDialog(it)
                    }
                }
                is State.Error -> {
                    Log.e("TAG", "error ${this.error?.message}")
                }
            }
        }
    }

    override fun onMapReady(mapGoogle: GoogleMap) {
        mapGoogle.setOnMapClickListener {
            if (NetworkUtils(this.requireContext()).isConnected()) {
                mapGoogle.clear()
                mapGoogle.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))
                viewModel.loadWeatherData(it.latitude, it.longitude)
            } else Toast.makeText(
                requireContext(),
                resources.getString(R.string.not_internet),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        binding.map.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.map.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }

    private fun showInfoDialog(currently: Currently) {
        val dialog = Dialog(requireContext())
        val root =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_layout, null, false)
        DataBindingUtil.bind<DialogLayoutBinding>(root)?.let { it.currently = currently }
        dialog.setContentView(root)
        dialog.show()
    }
}
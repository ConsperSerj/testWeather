package com.example.water.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.water.R
import com.example.water.data.model.Currently
import com.example.water.presentation.view.adapters.WeatherRecyclerViewAdapter
import com.example.water.presentation.viewmodel.LocationWeatherViewModel
import com.example.water.util.IOnDataChangeListener
import com.example.water.util.State
import com.example.water.util.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.location_fragment.*
import java.lang.ref.WeakReference

@AndroidEntryPoint
class LocationFragment : Fragment(R.layout.location_fragment) {
    private val lat: Double
        get() = arguments?.getDouble(KEY_LAN) ?: 0.0
    private val lon: Double
        get() = arguments?.getDouble(KEY_LON) ?: 0.0

    private var dataChangeListener = WeakReference<IOnDataChangeListener<Currently>>(null)
    private val viewModel: LocationWeatherViewModel by viewModels()
    private val adapter = WeatherRecyclerViewAdapter(listOf())


    override fun onAttach(context: Context) {
        super.onAttach(context)
        findOnDataUpdateListener()?.let { dataChangeListener = WeakReference(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observe(viewModel.weather) {
            when (this) {
                is State.Success -> {
                    dataChangeListener.get()?.onDataChanged(data?.currently)
                    data?.daily?.data?.let {
                        adapter.updateData(it)
                    }
                }
                is State.Error -> {
                    Log.e("TAG", "error ${this.error?.message}")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadWeatherData(lat, lon)
    }

    tailrec fun findOnDataUpdateListener(fragment: Fragment? = this): IOnDataChangeListener<Currently>? =
        fragment?.parentFragment as? IOnDataChangeListener<Currently> ?: findOnDataUpdateListener(
            parentFragment
        )

    companion object {
        const val KEY_LAN = "lan"
        const val KEY_LON = "lon"

        fun newInstance(lat: Float, lon: Float) = LocationFragment().apply {
            arguments = bundleOf(
                KEY_LAN to lat,
                KEY_LON to lon
            )
        }
    }

}
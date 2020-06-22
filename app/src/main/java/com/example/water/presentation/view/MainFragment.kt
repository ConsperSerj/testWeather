package com.example.water.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.water.R
import com.example.water.data.model.Currently
import com.example.water.databinding.MainFragmentBinding
import com.example.water.presentation.view.adapters.ViewPagerAdapter
import com.example.water.util.IOnDataChangeListener
import com.example.water.util.NetworkUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MainFragment : Fragment(), IOnDataChangeListener<Currently> {
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment,
            container, false
        )

        if (NetworkUtils(this.requireContext()).isConnected()) {
            binding.viewPager.adapter = ViewPagerAdapter(this)
            TabLayoutMediator(binding.tabs, binding.viewPager,
                TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                    tab.text = resources.getString(LOCATION_NAME_ARRAY[position])
                }
            ).attach()
        } else Toast.makeText(
            requireContext(),
            resources.getString(R.string.not_internet),
            Toast.LENGTH_LONG
        ).show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextMap.isVisible = NetworkUtils(this.requireContext()).isConnected()
        nextMap.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_mapFragment)
        }
    }

    override fun onDataChanged(currently: Currently?) {
        binding.currently = currently
    }

    companion object {
        val LOCATION_NAME_ARRAY =
            arrayListOf(R.string.tab_name_st_petersburg, R.string.tab_name_moscow)
    }
}
package com.example.water.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.water.data.model.DailyData
import com.example.water.databinding.ItemWeaterBinding
import com.example.water.util.DailyDataDiffUtilCallback

class WeatherRecyclerViewAdapter(var data: List<DailyData>) :
    RecyclerView.Adapter<ItemWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemWeatherViewHolder {
        return ItemWeatherViewHolder(
            ItemWeaterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemWeatherViewHolder, position: Int) {
        holder.binding.dailyData = data[position]
    }

    fun updateData(newData: List<DailyData>) {
        val diffUtilCallback = DailyDataDiffUtilCallback(this.data, newData)
        val dDiffResult = DiffUtil.calculateDiff(diffUtilCallback)
        this.data = newData
        dDiffResult.dispatchUpdatesTo(this)
    }
}

class ItemWeatherViewHolder(
    val binding: ItemWeaterBinding
) : RecyclerView.ViewHolder(binding.root)


package com.example.water.util

import androidx.recyclerview.widget.DiffUtil
import com.example.water.data.model.DailyData

class DailyDataDiffUtilCallback(
    private val oldList: List<DailyData>,
    private val newList: List<DailyData>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].time == newList[newItemPosition].time

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
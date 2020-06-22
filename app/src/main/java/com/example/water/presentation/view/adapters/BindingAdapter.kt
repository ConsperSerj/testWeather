package com.example.water.presentation.view.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.water.R
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("temperature")
    fun bindTemperatureToTextView(textView: TextView, temperature: Float?) {
        temperature?.let {
            if (temperature > 0) {
                val value = ((temperature - 32) * 5 / 9).toInt()
                textView.text = "$value \u2103"
            }
        }
    }

    @JvmStatic
    @BindingAdapter("date")
    fun bindDateToTextView(textView: TextView, date: Long?) {
        date?.let {
            val format = SimpleDateFormat("EEE, d MMM")
            textView.text = format.format(Date(it * 1000))
        }
    }

    //ТУТ Все не переберал, я думаю для примера будет достаточно
    @JvmStatic
    @BindingAdapter("icon")
    fun bindIconTypeToImageView(imageView: ImageView, type: String?) {
        when (type) {
            "clear-day" -> imageView.setImageResource(R.drawable.sun)
            "partly-cloudy-day" -> imageView.setImageResource(R.drawable.cloudy)
            "rain" -> imageView.setImageResource(R.drawable.ic_rain)
            "cloudy" -> imageView.setImageResource(R.drawable.cloudys)
            "clear-night" -> imageView.setImageResource(R.drawable.clear_night)
        }
    }

}
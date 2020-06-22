package com.example.water.util


interface IOnDataChangeListener<Currently> {

    fun onDataChanged(currently: Currently?)
}
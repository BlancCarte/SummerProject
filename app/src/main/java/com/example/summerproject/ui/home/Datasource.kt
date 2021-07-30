package com.example.summerproject.ui.home

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource(resources: Resources) {
    private val initialFlowerList = flowerList(resources)
    private val flowersLiveData = MutableLiveData(initialFlowerList)


    fun getFlowerList(): LiveData<List<Flower>> {
        return flowersLiveData
    }


    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
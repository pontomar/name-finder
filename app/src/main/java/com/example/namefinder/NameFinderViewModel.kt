package com.example.namefinder

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

class NameFinderViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    // Use State for the lists of names
    var girlNames by mutableStateOf<List<BabyName>>(emptyList())
        private set
    var boyNames by mutableStateOf<List<BabyName>>(emptyList())
        private set

    private val _selectedNames = mutableListOf<BabyName>()
    val selectedNames: List<BabyName> get() = _selectedNames

    init {
        // Load names from CSV files when ViewModel is created
        loadNames()
    }

    fun addSelectedName(name: BabyName){
        _selectedNames.add(name)
    }


    fun removeSelectedName(name: BabyName){
        _selectedNames.remove(name)
    }

    fun getSelectNames(): List<BabyName> {
        return selectedNames
    }

    // Function to load names from CSV file
    private fun loadNames() {
        girlNames = loadNamesFromCsv("girls.csv")
        boyNames = loadNamesFromCsv("boys.csv")
    }

    // Function to load names from the given CSV file
    private fun loadNamesFromCsv(fileName: String): List<BabyName> {
        return try {
            val inputStream = context.assets.open(fileName)
            csvReader().readAllWithHeader(inputStream).map { row ->
                BabyName(
                    name = row["name"] ?: "", // Adjust to match the actual header
                    unisex = row["gender"]?.toIntOrNull() ?: 0,
                    biblical = row["biblical"]?.toIntOrNull() ?: 0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // Return an empty list on error
        }
    }
}

data class BabyName(
    val name: String,
    val unisex: Int,
    val biblical: Int
)

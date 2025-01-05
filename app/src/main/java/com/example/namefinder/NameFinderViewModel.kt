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

    private val _selectedNames = mutableStateOf<List<BabyName>>(listOf())
    val selectedNames: List<BabyName> get() = _selectedNames.value

    init {
        // Load names from CSV files when ViewModel is created
        loadNames()
    }

    fun addSelectedName(babyName: BabyName){
        _selectedNames.value += babyName
    }


    fun removeSelectedName(babyName: BabyName){
        _selectedNames.value = _selectedNames.value.filter {it != babyName}
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
                    altSpelling = row["alt_spellings"] ?: "",
                    biblical = row["biblical"]?.toIntOrNull() ?: 0,
                    gender = Gender.UNDEFINED
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // Return an empty list on error
        }
    }
}

private fun assignGender(names: List<BabyName>, inputGender: Gender?): List<BabyName> {
    return names.map { babyName ->
        if (inputGender != null) {
            babyName.copy(gender = inputGender)
        } else {
            babyName
        }
    }
}

data class BabyName(
    val name: String,
    val unisex: Int,
    val altSpelling: String,
    val biblical: Int,
    val gender: Gender
)

enum class Gender {
    FEMALE, MALE, UNDEFINED
}
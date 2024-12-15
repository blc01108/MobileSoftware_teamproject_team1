package com.example.mobilesw_project.data

import android.net.Uri

data class Meal(
    val name: String,
    val mealType: String,
    val sideDishes: List<String> = emptyList(),
    val date: String,
    val location: String,
    val imageUri: Uri?,
    val price: String,
    val calorie: String,
    val evaluation: String
)

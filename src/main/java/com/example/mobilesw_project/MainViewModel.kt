package com.example.mobilesw_project

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mobilesw_project.data.Meal

class MainViewModel : ViewModel() {
    val meals = mutableStateListOf<Meal>()

    fun addMeal(meal: Meal) {
        meals.add(meal)
    }

    fun getTotalCalories(): Int {
        return meals.sumOf { it.calorie.toInt() }
    }

    fun getTotalBreakfastCost(): Int {
        return meals.filter { it.mealType == "조식" }.sumOf { it.price.toInt() }
    }

    fun getTotalLunchCost(): Int {
        return meals.filter { it.mealType == "중식" }.sumOf { it.price.toInt() }
    }

    fun getTotalDinnerCost(): Int {
        return meals.filter { it.mealType == "석식" }.sumOf { it.price.toInt() }
    }

    fun getTotalSnackCost(): Int {
        return meals.filter { it.mealType == "간식" }.sumOf { it.price.toInt() }
    }
}
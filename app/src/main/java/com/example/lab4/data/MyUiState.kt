package com.example.lab4.data

import com.example.lab4.ui.Category
import com.example.lab4.ui.Recommendation

data class MyUiState(
//    val guess: String = "",
    val categoryList: List<Category> = listOf(),
    val currentCategory: Category? = null,
    val currentRecommendation: Recommendation? = null
)